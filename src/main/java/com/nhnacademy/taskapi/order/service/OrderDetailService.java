package com.nhnacademy.taskapi.order.service;

import com.nhnacademy.taskapi.order.dto.BookOrderRequest;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(Order order, List<BookOrderRequest> bookOrderRequests);
}
