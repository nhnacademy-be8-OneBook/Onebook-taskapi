package com.nhnacademy.taskapi.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderRequest {
    long orderDetailId;
    long bookId;
    int quantity;
    int discountAmount;
    int price;
    int discountedPrice;
    String couponNumber;
}
