package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderResponse {
    private long orderId;
    private String orderName;
    private String orderer;
    private LocalDateTime dateTime;
    private int totalPrice;
    private String orderStatusName;

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getOrderName(),
                order.getOrdererName(),
                order.getDateTime(),
                order.getTotalPrice(),
                order.getOrderStatus().getStatusName()
        );
    }
}