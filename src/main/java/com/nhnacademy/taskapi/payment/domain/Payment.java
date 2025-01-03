package com.nhnacademy.taskapi.payment.domain;

import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 결제(Payment) 엔티티
 * 향후 다른 결제수단(네이버페이, 페이코 등)도 여기서 공통 정보를 저장
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @Column(nullable = false)
    private long point = 0;  // default, 결제 시 내가 사용할 포인트 금액

    @NotNull
    private String status = "READY";

    @NotNull
    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    @NotNull
    private int totalAmount;

    @NotNull
    @Column(length = 10)
    private String currency = "KRW"; // default

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_key", referencedColumnName = "paymentKey")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
