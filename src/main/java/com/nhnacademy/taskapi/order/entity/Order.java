package com.nhnacademy.taskapi.order.entity;

import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.orderlist.entity.OrderList;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Table(name = "order")
@Entity
public class Order {
    @Id
    @Column(name = "order_id")
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "phone_number")
    String phoneNumber;
    Date date;
    @Column(name = "delivery_price")
    int deliveryPrice;
    @Column(name = "total_price")
    int totalPrice;

    // 양방향
    @OneToMany
    @JoinColumn(name = "orderlist_id")
    List<OrderList> orderListList;
}