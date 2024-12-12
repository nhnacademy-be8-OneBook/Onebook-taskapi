package com.nhnacademy.taskapi.orders.entity;

import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.orderlist.entity.OrdersList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Entity
public class Orders {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ordersId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

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

    @OneToMany(mappedBy = "orders")
    List<OrdersList> ordersListList;

    public Orders(String phoneNumber, LocalDateTime dateTime, int deliveryPrice, int totalPrice) {
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
    }
}