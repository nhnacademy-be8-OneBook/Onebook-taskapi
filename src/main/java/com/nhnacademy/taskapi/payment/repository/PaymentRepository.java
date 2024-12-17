package com.nhnacademy.taskapi.payment.repository;

import com.nhnacademy.taskapi.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
