package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderDetailDTO;
import com.nhnacademy.taskapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody OrderCreateDTO orderCreateDTO) {
        orderService.saveOrder(memberId, orderCreateDTO);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDetailDTO>> getOrders(@RequestHeader("X-MEMBER-ID") Long memberId) {

        List<OrderDetailDTO> orderList = orderService.getOrders(memberId);
        return ResponseEntity.ok().body(orderList);
    }
}