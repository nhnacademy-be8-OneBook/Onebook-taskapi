package com.nhnacademy.taskapi.delivery.entity;

import com.nhnacademy.taskapi.delivery.domain.Status;
import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    Long deliveryId;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    Order order;

    LocalDate startDate;
    LocalDate doneDate;
    String recipientName;
    String recipientPhoneNumber;
    String recipientAddress;
    Status status;

    // 송장번호
    String invoiceNumber;

}
