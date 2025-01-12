package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderFormRequest;
import com.nhnacademy.taskapi.order.dto.OrderResponseDto;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;
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
    public ResponseEntity<Long> createOrder(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody OrderFormRequest orderFormRequest) {
        long newOrderId = orderService.saveOrder(memberId, orderFormRequest);
        return ResponseEntity.created(null).body(newOrderId);

    @GetMapping("/task/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestHeader("X-MEMBER-ID") Long memberId) {
        List<OrderResponseDto> orderList = orderService.getOrderList(memberId);

        // null일 경우 list 반환 방법
        if (orderList == null || orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/task/admin/orders")
    public ResponseEntity<List<OrderStatusResponseDto>> getOrdersByStatusName(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestParam String status) {
        List<OrderStatusResponseDto> ordersByStatusName = orderService.getOrdersByStatusName(status);
        return ResponseEntity.ok(ordersByStatusName);
    }

    // 주문 상태 변경하기
    @PutMapping("/task/admin/orders")
    public void updateOrderStatus(@RequestBody List<Long> orderIds, @RequestParam String status) {
        System.out.println(orderIds);
        System.out.println(status);

        orderService.updateOrderStatus(orderIds, status);

    }

}