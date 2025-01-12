package com.nhnacademy.taskapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormRequest {
    private List<BookOrderRequest> items;
    private DeliveryRequest delivery;
    private int packagingId;
}
