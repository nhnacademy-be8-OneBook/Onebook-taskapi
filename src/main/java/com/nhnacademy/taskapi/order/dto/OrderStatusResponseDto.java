package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderStatusResponseDto {
    private String orderStatusName;

    public static OrderStatusResponseDto fromOrderStatus(OrderStatus orderStatus) {
        return new OrderStatusResponseDto(orderStatus.getStatusName());
    }
}
