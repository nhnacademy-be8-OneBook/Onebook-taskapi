package com.nhnacademy.taskapi.payment.service.impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.payment.domain.Payment;
import com.nhnacademy.taskapi.payment.dto.PaymentRequest;
import com.nhnacademy.taskapi.payment.dto.PaymentResponse;
import com.nhnacademy.taskapi.payment.dto.CheckoutInfoResponse;
import com.nhnacademy.taskapi.payment.exception.*;
import com.nhnacademy.taskapi.payment.repository.PaymentMethodRepository;
import com.nhnacademy.taskapi.payment.repository.PaymentRepository;
import com.nhnacademy.taskapi.payment.service.PaymentService;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final JpaPointRepository pointRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    @Transactional
    public PaymentResponse createPayment(String orderIdStr, Long memberId, PaymentRequest paymentRequest) {
        // 1. orderIdStr → Long 변환
        Long realOrderId = parseOrderId(orderIdStr);

        // 2. 이미 결제된 주문인지 확인
        Payment existingPayment = paymentRepository.findByOrder_OrderId(realOrderId);
        if (existingPayment != null) {
            throw new InvalidPaymentException("이미 결제된 주문입니다.");
        }

        // 3. 주문 존재 확인
        Order order = orderRepository.findById(realOrderId)
                .orElseThrow(() -> new PaymentNotFoundException("주문을 찾을 수 없습니다."));

        // 4. 주문 소유자 확인
        if (!order.getMember().getId().equals(memberId)) {
            throw new InvalidPaymentException("본인 주문이 아닙니다.");
        }

        // 5. 포인트 사용 확인
        Point point = pointRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new InvalidPaymentException("회원 포인트를 찾을 수 없습니다."));
        long userPoint = point.getAmount();
        if (paymentRequest.getUsedPoint() > userPoint) {
            throw new InsufficientPointException("사용할 포인트가 보유한 포인트보다 많습니다.");
        }

        // 6. **결제금액 서버 계산**
        int orderTotalAmount = order.getTotalPrice();  // 주문 총액 (DB)
        long usedPoint = paymentRequest.getUsedPoint();
        int finalPayAmount = orderTotalAmount - (int) usedPoint;
        if (finalPayAmount < 0) {
            throw new InvalidPaymentException("포인트 사용액이 주문 금액을 초과합니다.");
        }

        // 7. Payment 엔티티 생성 (status=READY)
        Payment newPayment = new Payment();
        newPayment.setOrder(order);
        newPayment.setStatus("READY");
        newPayment.setRequestedAt(LocalDateTime.now());
        newPayment.setTotalAmount(finalPayAmount); // 서버 계산 결과
        newPayment.setCurrency(
                paymentRequest.getCurrency() != null
                        ? paymentRequest.getCurrency()
                        : "KRW"
        );

        newPayment.setPoint(usedPoint);
        // PaymentKey는 결제승인 시점에 세팅됨
        Payment savedPayment = paymentRepository.save(newPayment);

        // 8. 포인트 차감
        // TODO 결제 취소 시 복원 로직 필요...?
        point.setAmount((int) (userPoint - usedPoint));
        pointRepository.save(point);

        // 9. 응답: 클라이언트가 최종 결제금액(finalPayAmount)을 알 수 있도록
        return PaymentResponse.builder()
                .paymentId(savedPayment.getPaymentId())
                .orderId(orderIdStr)
                .paymentKey(null) // 승인 전
                .totalAmount(savedPayment.getTotalAmount()) // 최종 계산된 금액
                .currency(savedPayment.getCurrency())
                .status(savedPayment.getStatus()) // READY
                .requestedAt(savedPayment.getRequestedAt())
                .approvedAt(savedPayment.getApprovedAt()) // null
                .usePoint(usedPoint)
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("결제 정보를 찾을 수 없습니다."));

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrder().getOrderId().toString()) // String으로 변환하여 포함
                .paymentKey(
                        payment.getPaymentMethod() != null
                                ? payment.getPaymentMethod().getPaymentKey()
                                : null
                )
                .totalAmount(payment.getTotalAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .usePoint(payment.getPoint())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CheckoutInfoResponse getCheckoutInfo(String orderIdStr, Long memberId) {
        // 1. orderIdStr을 Long으로 변환
        Long realOrderId = parseOrderId(orderIdStr);

        // 2. 주문 존재 확인
        Order order = orderRepository.findById(realOrderId)
                .orElseThrow(() -> new PaymentNotFoundException("주문을 찾을 수 없습니다."));

        // 3. 본인 주문인지 확인
        if (!order.getMember().getId().equals(memberId)) {
            throw new PaymentNotFoundException("본인 주문이 아닙니다.");
        }

        // 4. 회원, 포인트 정보
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new PaymentNotFoundException("회원이 존재하지 않습니다."));
        Point point = pointRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new PaymentNotFoundException("포인트 정보가 없습니다."));


        // 5. 응답 DTO 작성
        CheckoutInfoResponse dto = new CheckoutInfoResponse();

        dto.setOrderId(orderIdStr); // 원본 orderIdStr 포함
        dto.setOrderAmount(order.getTotalPrice());
        dto.setUserPoint(point.getAmount());
        dto.setOrderName(order.getOrderName());

        // 주문자 정보
        dto.setOrdererName(member.getName());
        dto.setOrdererEmail(member.getEmail());
        dto.setOrdererPhone(member.getPhoneNumber());

        dto.setMemberId(memberId);

        return dto;
    }

    /**
     * 문자열 orderIdStr → Long 변환 유틸
     */
    private Long parseOrderId(String orderIdStr) {
        if (orderIdStr == null) {
            throw new PaymentNotFoundException("orderId is null");
        }

        // 언더바가 있으면 앞부분만 가져옴
        if (orderIdStr.contains("_")) {
            String pureLongPart = orderIdStr.substring(0, orderIdStr.indexOf("_"));
            try {
                return Long.parseLong(pureLongPart);
            } catch (NumberFormatException e) {
                throw new PaymentNotFoundException("유효하지 않은 orderId 형식입니다.");
            }
        } else {
            try {
                return Long.parseLong(orderIdStr);
            } catch (NumberFormatException e) {
                throw new PaymentNotFoundException("유효하지 않은 orderId 형식입니다.");
            }
        }
    }
}
