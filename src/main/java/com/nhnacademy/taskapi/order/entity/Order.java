package com.nhnacademy.taskapi.order.entity;

import com.nhnacademy.taskapi.delivery.entity.Delivery;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.orderlist.entity.OrderList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @Column(name = "orderer")
    String orderer;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "date_time")
    LocalDateTime dateTime;
    @Column(name = "delivery_price")
    int deliveryPrice;
    @Column(name = "total_price")
    int totalPrice;

    // 양방향
//    @OneToMany
//    @JoinColumn(name = "orderlist_id")

    @OneToMany(mappedBy = "order")
    List<OrderList> orderListList;

    @OneToMany(mappedBy = "order")
    List<Delivery> deliveryList;

    public Order(Member member, String orderder, String phoneNumber, LocalDateTime dateTime, int deliveryPrice, int totalPrice) {
        this.member = member;
        this.orderer = orderer;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordersId=" + orderId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateTime=" + dateTime +
                ", deliveryPrice=" + deliveryPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}