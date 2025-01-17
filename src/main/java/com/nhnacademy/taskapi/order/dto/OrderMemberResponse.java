package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderMemberResponse {
    Long orderId;
    Long memberId;
    String orderName;
    String ordererName;
    String ordererPhoneNumber;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
    String statusName;
    int packagingPrice;

   public static OrderMemberResponse fromOrder(Order order) {
       return new OrderMemberResponse(
               order.getOrderId(),
               order.getMember().getId(),
               order.getOrderName(),
               order.getOrdererName(),
               order.getOrdererPhoneNumber(),
               order.getDateTime(),
               order.getDeliveryPrice(),
               order.getTotalPrice(),
               order.getOrderStatus().getStatusName(),
               order.getPackagingPrice()
       );
   }
}
