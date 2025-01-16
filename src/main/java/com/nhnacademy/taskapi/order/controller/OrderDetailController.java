package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderDetailResponse;
import com.nhnacademy.taskapi.order.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/task/order-detail/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@RequestHeader("X-MEMBER-ID") Long memberId, @PathVariable("orderId") Long orderId) {
        OrderDetailResponse orderDetailResponse = orderDetailService.getOrderDetail(memberId, orderId);

        return ResponseEntity.ok(orderDetailResponse);
    }

}
