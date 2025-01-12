package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderStatusResponseDto {
    // 주문id
    // 회원id
    // 주문상태
    // 주문이름
    // 휴대폰번호
    // 주문일시
    // 배송비
    // 주문금액
    Long orderId;
    Long memberId;
    String orderStatus;
    String orderName;
    String orderer;
    String phoneNumber;
    LocalDateTime orderTime;
    LocalDate deliveryTime;
    int deliveryPrice;
    int totalPrice;

    // TODO memberId 적용하기
//    public static OrderStatusResponseDto fromOrderStatus(Long memberId, Order order) {
    public static OrderStatusResponseDto fromOrderStatus(Order order) {
        return new OrderStatusResponseDto(
            order.getOrderId(),
            order.getMember().getId(),
            order.getOrderStatus().getStatusName(),
            order.getOrderName(),
            order.getOrdererName(),
            order.getOrdererPhoneNumber(),
//            order.getDateTime(),
            null,
//            order.getDeliveryList().get(0).getDoneDate(),
            null,
            order.getDeliveryPrice(),
            order.getTotalPrice());
    }
}
