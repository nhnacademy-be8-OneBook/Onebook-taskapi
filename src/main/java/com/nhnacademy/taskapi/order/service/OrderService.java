package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderFormRequest;
import com.nhnacademy.taskapi.order.dto.OrderResponse;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponse;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    long processOrder(Long memberId, OrderFormRequest orderFormRequest);
    Page<OrderResponse> getOrderList(Long memberId, Pageable pageable);
    OrdererResponseDto getOrderer(Long memberId);
    List<OrderStatusResponse> getOrdersByStatusName(String statusName);
    void updateOrderStatus(List<Long> orderIds, String status);
}
