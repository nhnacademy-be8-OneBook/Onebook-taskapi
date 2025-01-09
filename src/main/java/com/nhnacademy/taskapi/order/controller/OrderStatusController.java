package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;
import com.nhnacademy.taskapi.order.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping("/task/order/statuses")
    public ResponseEntity<List<String>> getOrderStatuses() {
        return ResponseEntity.ok(orderStatusService.getOrderStatusesList());
    }
}
