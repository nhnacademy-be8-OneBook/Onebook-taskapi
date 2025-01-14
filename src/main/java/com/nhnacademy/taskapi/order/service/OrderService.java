package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderFormRequest;
import com.nhnacademy.taskapi.order.dto.OrderResponse;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponse;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;

import java.util.List;

public interface OrderService {
    long processOrder(Long memberId, OrderFormRequest orderFormRequest);
    List<OrderResponse> getOrderList(Long memberId);
    OrdererResponseDto getOrderer(Long memberId);
    List<OrderStatusResponse> getOrdersByStatusName(String statusName);
    void updateOrderStatus(List<Long> orderIds, String status);
}
