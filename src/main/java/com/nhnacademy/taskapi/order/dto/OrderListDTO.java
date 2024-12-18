package com.nhnacademy.taskapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderListDTO {
    String orderer;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
}