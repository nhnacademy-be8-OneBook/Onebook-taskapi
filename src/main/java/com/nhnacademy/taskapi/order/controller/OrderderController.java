package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderderController {
    private OrderService orderService;

    @GetMapping("/task/orderer")
    public ResponseEntity<Void> orderer(@RequestHeader("X-MEMBER-ID") Long memberId) {
        orderService.getOrderer(memberId);
        return ResponseEntity.ok().build();
    }
}