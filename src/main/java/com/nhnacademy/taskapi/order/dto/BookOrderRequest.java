package com.nhnacademy.taskapi.order.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BookOrderRequest {
    long bookId;
    int quantity;
    int price;
}
