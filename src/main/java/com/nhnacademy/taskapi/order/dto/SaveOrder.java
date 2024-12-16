package com.nhnacademy.taskapi.order.dto;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class SaveOrder {
    Long orderId;
    String customerId;
    String phoneNumber;
    Date date;
    int deliveryPrice;
    int totalPrice;
}