package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderFormRequest;
import com.nhnacademy.taskapi.order.dto.OrderResponseDto;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;

import java.util.List;

public interface OrderService {
    long saveOrder(Long member_id, OrderFormRequest orderFormRequest);
    List<OrderResponseDto> getOrderList(Long memberId);
    OrdererResponseDto getOrderer(Long memberId);
    List<OrderStatusResponseDto> getOrdersByStatusName(String statusName);
    void updateOrderStatus(List<Long> orderIds, String status);
    long processOrder(Long memberId, OrderFormRequest orderFormRequest);
}
