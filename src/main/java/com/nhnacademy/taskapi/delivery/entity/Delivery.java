package com.nhnacademy.taskapi.delivery.entity;

import com.nhnacademy.taskapi.delivery.domain.Status;
import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long delivery_id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    Order order;

    LocalDate start_date;
    LocalDate done_date;
    String phone_number;
    String recipient;
    String location;
    Status status;
    String invoice_number;

}
