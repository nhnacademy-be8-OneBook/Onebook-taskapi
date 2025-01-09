package com.nhnacademy.taskapi.delivery.entity;

import com.nhnacademy.taskapi.delivery.domain.Status;
import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

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
    String phoneNumber;
    String recipient;
    String location;
    Status status;

    // 송장번호
    String invoiceNumber;

}
