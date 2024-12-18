package com.nhnacademy.taskapi.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class OrderCreateDTO {
    String orderer;
    String phoneNumber;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
}
