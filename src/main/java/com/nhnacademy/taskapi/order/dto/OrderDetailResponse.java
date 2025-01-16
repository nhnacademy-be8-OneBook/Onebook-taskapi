package com.nhnacademy.taskapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {
    private List<OrderDetailBookResponse> items;
    private OrderResponse orderResponse;
}
