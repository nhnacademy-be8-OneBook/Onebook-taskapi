package com.nhnacademy.taskapi.orderdetail.entity;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.orderdetail.domain.OrderDetailStatus;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @Column(name = "orderlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderlistId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
    /*
    Coupon coupon;
    */

    @Column(name = "book_price")
    int bookPrice;
    @Enumerated(EnumType.STRING)
    OrderDetailStatus status;

    public OrderDetail(int packagingPrice, OrderDetailStatus status, int bookPrice) {
        this.order = order;
        this.status = status;
        this.bookPrice = bookPrice;
    }
}
