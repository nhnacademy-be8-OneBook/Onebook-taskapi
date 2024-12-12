package com.nhnacademy.taskapi.orderlist.entity;

import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.orderlist.entity.domain.OrderListStatus;
import jakarta.persistence.*;

@Entity
public class OrderList {
    @Id
    @Column(name = "orderlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderListId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

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
    OrderListStatus status;
}