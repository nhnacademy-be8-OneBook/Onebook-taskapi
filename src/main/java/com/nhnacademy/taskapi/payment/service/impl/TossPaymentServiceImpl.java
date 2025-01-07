package com.nhnacademy.taskapi.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.payment.domain.Payment;
import com.nhnacademy.taskapi.payment.domain.PaymentMethod;
import com.nhnacademy.taskapi.payment.dto.toss.TossConfirmRequest;
import com.nhnacademy.taskapi.payment.dto.toss.TossConfirmResponse;
import com.nhnacademy.taskapi.payment.exception.PaymentNotFoundException;
import com.nhnacademy.taskapi.payment.repository.PaymentMethodRepository;
import com.nhnacademy.taskapi.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TossPaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 테스트용 시크릿 키
    private static final String TOSS_SECRET_KEY = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";

    // 토스 결제 승인 처리
    @Transactional
    public TossConfirmResponse confirmTossPayment(TossConfirmRequest request) {
        // 1) orderId로 Payment 조회
        String orderIdStr = request.getOrderId();
        Long realOrderId = parseOrderId(orderIdStr);

        Payment payment = paymentRepository.findByOrder_OrderId(realOrderId);
        if (payment == null) {
            throw new PaymentNotFoundException("해당 주문의 결제 정보가 존재하지 않습니다.");
        }

        // 상태 확인
        if (!"READY".equals(payment.getStatus())) {
            throw new IllegalStateException("이미 결제 완료이거나 취소된 상태입니다.");
        }

        // 2) Toss 승인 API 호출 준비
        String base64Secret = Base64.getEncoder()
                .encodeToString((TOSS_SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + base64Secret);

        // 토스에서 응답값을 영어로 가져오기 위해..
        // 한글이 깨짐..
        headers.set("Accept-Language", "en-US");

        // 결제 승인 금액 (문자열 → int)
        int approveAmount;
        try {
            approveAmount = Integer.parseInt(request.getAmount());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 결제 금액입니다.");
        }

        // DB상 결제금액과 일치 검증 (optional)
        // TODO : 추후 포인트 적용하려면 수정이 필요할 듯
        if (payment.getTotalAmount() != approveAmount) {
            throw new IllegalStateException("승인 요청 금액과 DB 금액이 다릅니다.");
        }

        // 3) Request Body
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", request.getPaymentKey());
        body.put("orderId", request.getOrderId());
        body.put("amount", approveAmount);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        } catch (Exception e) {
            log.error("[TaskAPI] 토스 결제 승인 API 실패", e);
            throw new RuntimeException("토스 결제 승인 API 요청 실패", e);
        }

        // 응답이 200 OK가 아니면 예외
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("토스 결제승인 실패: " + responseEntity.getBody());
        }

        // 4) 응답 JSON 파싱
        Map<String, Object> tossRes;
        try {
            tossRes = objectMapper.readValue(responseEntity.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("응답 JSON 파싱 오류: " + responseEntity.getBody());
        }

        // 예: status="DONE", approvedAt="2024-12-11T15:20:33+09:00", method="카드", "간편결제"...
        String status = (String) tossRes.get("status");
        String approvedAtStr = (String) tossRes.get("approvedAt");
        String methodStr = (String) tossRes.get("method"); // "카드", "간편결제", ...

        // 5) card 객체
        Map<String, Object> cardObj = (Map<String, Object>) tossRes.get("card"); // null이면 간편결제 or 가상계좌

        // PaymentMethod가 없다면 새로 생성 (paymentKey 등)
        PaymentMethod pm = payment.getPaymentMethod();
        if (pm == null) {
            pm = new PaymentMethod();
            pm.setPaymentKey(request.getPaymentKey());
            pm.setType("TOSS");
            pm.setMethod(methodStr); // "카드", "간편결제" ...
        }

        if (cardObj != null) {
            // 카드 정보 저장
            pm.setCardOwnerType((String) cardObj.get("ownerType"));
            pm.setCardNumber((String) cardObj.get("number"));
            pm.setCardAmount((Integer) cardObj.get("amount"));
            pm.setCardIssuerCode((String) cardObj.get("issuerCode"));
            pm.setCardAcquirerCode((String) cardObj.get("acquirerCode"));
            pm.setCardIsInterestFree((Boolean) cardObj.get("isInterestFree"));
            pm.setCardType((String) cardObj.get("cardType"));
            pm.setCardApproveNo((String) cardObj.get("approveNo"));
            pm.setCardInstallmentPlanMonths((Integer) cardObj.get("installmentPlanMonths"));
        }
        // 카드가 null -> 간편결제/가상계좌 등 다른 로직 처리 가능

        paymentMethodRepository.save(pm);
        payment.setPaymentMethod(pm);

        // 6) Payment 업데이트
        payment.setStatus(status); // "DONE"
        try {
            payment.setApprovedAt(LocalDateTime.parse(approvedAtStr));
        } catch (DateTimeParseException ex) {
            payment.setApprovedAt(LocalDateTime.now());
        }

        paymentRepository.save(payment);

        // 7) 응답 DTO
        TossConfirmResponse result = new TossConfirmResponse();
        result.setPaymentKey(request.getPaymentKey());
        result.setOrderId(orderIdStr); // 원본 orderIdStr 포함
        result.setStatus(status);
        result.setApprovedAt(payment.getApprovedAt());
        result.setMessage("결제 승인 성공 (카드/간편결제 등 정보가 저장되었습니다.)");
        result.setMemberId(result.getMemberId());

        return result;
    }

    /**
     * 문자열 orderIdStr → Long 변환
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
