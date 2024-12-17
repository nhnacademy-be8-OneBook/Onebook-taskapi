package com.nhnacademy.taskapi.orderpolicy.entity;

import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;

@Entity
@Table(name = "refunds")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    Long id;

    @ManyToOne
            @JoinColumn(name = "orders_id")
    Order order;

    String reason;
    String explain;
    int price;


}
