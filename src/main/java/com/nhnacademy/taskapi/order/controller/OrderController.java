package com.nhnacademy.taskapi.order.controller;

import com.nhnacademy.taskapi.order.dto.OrderFormRequest;
import com.nhnacademy.taskapi.order.dto.OrderResponse;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponse;
import com.nhnacademy.taskapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/task/order")
    public ResponseEntity<Long> createOrder(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody OrderFormRequest orderFormRequest) {
//        long newOrderId = orderService.saveOrder(memberId, orderFormRequest);
        long newOrderId = orderService.processOrder(memberId, orderFormRequest);
        return ResponseEntity.created(null).body(newOrderId);
    }

    @GetMapping("/task/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(@RequestHeader("X-MEMBER-ID") Long memberId, Pageable pageable) {
        Page<OrderResponse> orderList = orderService.getOrderList(memberId, pageable);

        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/task/orders/waiting")
    public ResponseEntity<Page<OrderResponse>> getWaitingOrders(@RequestHeader("X-MEMBER-ID") Long memberId, Pageable pageable) {
        String statusName = "결제대기";
        Page<OrderResponse> orderList = orderService.getOrderListByStatusName(memberId, statusName, pageable);

        // TODO null일 경우 list 반환 방법
        if (orderList == null || orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/task/admin/orders")
    public ResponseEntity<List<OrderStatusResponse>> getOrdersByStatusName(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestParam String status) {
        List<OrderStatusResponse> ordersByStatusName = orderService.getOrdersByStatusName(status);
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