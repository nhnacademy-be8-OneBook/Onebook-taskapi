package com.nhnacademy.taskapi.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Order {
    @Id
    Long orderId;

}
