package com.nhnacademy.taskapi.orderlist.entity;

import com.nhnacademy.taskapi.orderlist.domain.OrderListStatus;
import com.nhnacademy.taskapi.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_list")
public class OrderList {
    @Id
    @Column(name = "orderlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderlistId;

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

    public OrderList(int packagingPrice, OrderListStatus status, int bookPrice) {
        this.order = order;
        this.packagingPrice = packagingPrice;
        this.status = status;
        this.bookPrice = bookPrice;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "orderlistId=" + orderlistId +
                ", bookPrice=" + bookPrice +
                ", packagingPrice=" + packagingPrice +
                ", status=" + status +
                '}';
    }
}
