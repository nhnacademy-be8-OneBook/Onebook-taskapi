package com.nhnacademy.taskapi.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Entity
@Table(name = "order_Statuses")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @UniqueElements
    @Column(name = "order_status_name")
    private String statusName;

    public OrderStatus(String statusName) {
        this.statusName = statusName;
    }
}
