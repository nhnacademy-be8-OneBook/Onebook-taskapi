package com.nhnacademy.taskapi.payment.domain;


import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @Column(nullable = false)
    private long point = 0; // default

    @NotNull
    private String status;

    @NotNull
    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    @NotNull
    private int totalAmount;

    @Column(nullable = false, length = 10)
    private String currency = "KRW"; // default


    @ManyToOne
    @JoinColumn(name = "payment_key")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
