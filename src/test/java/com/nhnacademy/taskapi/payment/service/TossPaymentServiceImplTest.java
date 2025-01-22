package com.nhnacademy.taskapi.payment.service;

import com.nhnacademy.taskapi.order.service.OrderService;
import com.nhnacademy.taskapi.payment.domain.Payment;
import com.nhnacademy.taskapi.payment.dto.toss.TossConfirmRequest;
import com.nhnacademy.taskapi.payment.exception.PaymentNotFoundException;
import com.nhnacademy.taskapi.payment.repository.PaymentRepository;
import com.nhnacademy.taskapi.payment.service.impl.TossPaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TossPaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderService orderService;
    @Mock
    CommonPaymentService commonPaymentService;

    @InjectMocks
    @Spy
    TossPaymentServiceImpl tossPaymentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Payment가 없으면 PaymentNotFoundException")
    void testConfirmTossPayment_whenPaymentNotFound_thenThrowException() {
        // GIVEN
        TossConfirmRequest req = new TossConfirmRequest();
        req.setOrderId("123");
        req.setPaymentKey("PK_abc");
        req.setAmount("1000");

        // paymentRepository 에서 null 반환 => PaymentNotFoundException 유발
        given(paymentRepository.findByOrder_OrderId(123L)).willReturn(null);

        // WHEN & THEN
        assertThatThrownBy(() -> tossPaymentService.confirmTossPayment(req))
                .isInstanceOf(PaymentNotFoundException.class)
                .hasMessageContaining("해당 주문의 결제 정보가 없습니다.");

        // verify
        then(paymentRepository).should().findByOrder_OrderId(123L);
        then(commonPaymentService).shouldHaveNoInteractions();
        then(orderService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Payment가 READY가 아니면 IllegalStateException")
    void testConfirmTossPayment_whenPaymentNotReady_thenThrow() {
        // GIVEN
        TossConfirmRequest req = new TossConfirmRequest();
        req.setOrderId("200");
        req.setPaymentKey("PK_abc");
        req.setAmount("5000");

        Payment notReadyPayment = new Payment();
        notReadyPayment.setStatus("DONE"); // READY가 아님

        given(paymentRepository.findByOrder_OrderId(200L)).willReturn(notReadyPayment);

        // WHEN & THEN
        assertThatThrownBy(() -> tossPaymentService.confirmTossPayment(req))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 결제완료(DONE) 혹은 취소 상태입니다.");

        then(commonPaymentService).shouldHaveNoInteractions();
        then(orderService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("승인 요청 금액과 DB의 Payment 금액 불일치 -> IllegalStateException")
    void testConfirmTossPayment_whenApproveAmountMismatch_thenThrow() {
        // GIVEN
        TossConfirmRequest req = new TossConfirmRequest();
        req.setOrderId("300");
        req.setPaymentKey("PK_xyz");
        req.setAmount("10000"); // 클라이언트서 넘어온 "승인요청금액" 문자열

        Payment payment = new Payment();
        payment.setStatus("READY");
        payment.setTotalAmount(5000); // DB에는 5천원으로 저장

        given(paymentRepository.findByOrder_OrderId(300L)).willReturn(payment);

        // WHEN & THEN
        assertThatThrownBy(() -> tossPaymentService.confirmTossPayment(req))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("승인 요청 금액과 DB 금액이 다릅니다.");

        then(commonPaymentService).shouldHaveNoInteractions();
        then(orderService).shouldHaveNoInteractions();
    }


    @Test
    @DisplayName("orderId에 _ 포함 & 숫자 변환 불가능 => PaymentNotFoundException")
    void testConfirmTossPayment_whenInvalidOrderId() {
        // GIVEN
        TossConfirmRequest req = new TossConfirmRequest();
        req.setOrderId("ABC_zzz");
        req.setPaymentKey("PK_abc");
        req.setAmount("1000");

        // WHEN & THEN
        assertThatThrownBy(() -> tossPaymentService.confirmTossPayment(req))
                .isInstanceOf(PaymentNotFoundException.class)
                .hasMessageContaining("유효하지 않은 orderId 형식입니다.");
    }
}
