package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderResponseDTO;
import com.nhnacademy.taskapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/task/order")
    public ResponseEntity<Void> createOrder(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody OrderCreateDTO orderCreateDTO) {
        orderService.saveOrder(memberId, orderCreateDTO);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/task/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@RequestHeader("X-MEMBER-ID") Long memberId) {
        List<OrderResponseDTO> orderList = orderService.getOrderList(memberId);

        // null일 경우 list 반환 방법
        if (orderList == null || orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(orderList);
    }




}