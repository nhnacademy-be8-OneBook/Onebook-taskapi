package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;

import java.util.List;

public interface OrderStatusService {
    List<String> getOrderStatusesList();
}
