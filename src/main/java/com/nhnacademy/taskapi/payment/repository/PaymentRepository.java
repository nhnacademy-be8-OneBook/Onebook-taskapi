package com.nhnacademy.taskapi.payment.repository;

import com.nhnacademy.taskapi.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // orderId로 Payment 조회 (토스 승인 시 필요)
    Payment findByOrder_OrderId(Long orderId);
}
