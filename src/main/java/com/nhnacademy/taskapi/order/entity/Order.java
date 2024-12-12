package com.nhnacademy.taskapi.order.entity;

import com.nhnacademy.taskapi.customer.domain.Customer;
import jakarta.persistence.*;

@Table(name = "order")
@Entity
public class Order {
    @Id
    @Column(name = "order_id")
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
}