package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {
    void saveOrder(Long member_id, OrderCreateDTO orderCreateDTO);
    List<OrderDetailDTO> getOrders(Long memberId);
}
