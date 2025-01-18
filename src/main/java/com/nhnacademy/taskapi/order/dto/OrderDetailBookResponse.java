package com.nhnacademy.taskapi.order.dto;

import com.nhnacademy.taskapi.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailBookResponse {
    private Long bookId;
    private String bookTitle;
    private int quantity;
    private int price;

    public static OrderDetailBookResponse fromOrderDetail(OrderDetail orderDetail) {
        System.out.println(orderDetail);
        return new OrderDetailBookResponse(
                orderDetail.getBook().getBookId(),
                orderDetail.getBook().getTitle(),
                orderDetail.getQuantity(),
                orderDetail.getBookPrice()
        );
    }
}
