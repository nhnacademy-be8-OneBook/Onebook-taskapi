package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    String orderer;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;

    public static OrderResponseDto fromOrder(Order order) {
        return new OrderResponseDto(
                order.getOrderer(),
                order.getDateTime(),
                order.getDeliveryPrice(),
                order.getTotalPrice()
        );
    }
}