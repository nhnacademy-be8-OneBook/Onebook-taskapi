package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderResponseDto;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;

import java.util.List;

public interface OrderService {
    void saveOrder(Long member_id, OrderCreateDTO orderCreateDTO);
    List<OrderResponseDto> getOrderList(Long memberId);
    OrdererResponseDto getOrderer(Long memberId);
    List<OrderStatusResponseDto> getOrdersByStatusName(String statusName);
    void updateOrderStatus(List<Long> orderIds, String status);
}
