package com.nhnacademy.taskapi.orderlist.entity;

import com.nhnacademy.taskapi.orderlist.domain.OrdersListStatus;
import com.nhnacademy.taskapi.orders.entity.Orders;
import jakarta.persistence.*;

@Entity
public class OrdersList {
    @Id
    @Column(name = "orderlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderListId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Orders orders;

    /*
    Packaging packaging
    Book book;
    Coupon coupon;
    */

    @Column(name = "book_price")
    int bookPrice;
    @Column(name = "packaging_price")
    int packagingPrice;
    @Enumerated(EnumType.STRING)
    OrdersListStatus status;
}