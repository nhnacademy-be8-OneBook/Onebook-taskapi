package com.nhnacademy.taskapi.orders.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class SaveOrders {
    Long orderId;
    String customerId;
    String phoneNumber;
    Date date;
    int deliveryPrice;
    int totalPrice;
}