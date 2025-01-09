package com.nhnacademy.taskapi.order.entity;

import com.nhnacademy.taskapi.delivery.entity.Delivery;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.orderdetail.entity.OrderDetail;
import io.hypersistence.utils.hibernate.id.Tsid;
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
    @Tsid
    @Column(name = "orders_id")
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
    @Column(name = "order_name")
    String orderName;

    // 양방향
//    @OneToMany
//    @JoinColumn(name = "orderlist_id")

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetailList;


    @OneToMany(mappedBy = "order")
    List<Delivery> deliveryList;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    OrderStatus orderStatus;

    public Order(Member member, String orderer, String phoneNumber, LocalDateTime dateTime, int deliveryPrice, int totalPrice) {
        this.member = member;
        this.orderer = orderer;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
    }

    public Order(Member member, String orderer, String phoneNumber, LocalDateTime dateTime, int deliveryPrice, int totalPrice, OrderStatus orderStatus) {
        this.member = member;
        this.orderer = orderer;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
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