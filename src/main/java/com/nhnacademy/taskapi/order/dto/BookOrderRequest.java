package com.nhnacademy.taskapi.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOrderRequest {
    long bookId;
    int quantity;
    int price;
}
