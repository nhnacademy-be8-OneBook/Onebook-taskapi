package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.order.dto.BookOrderRequest;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderDetail;
import com.nhnacademy.taskapi.order.repository.OrderDetailRepository;
import com.nhnacademy.taskapi.order.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final BookService bookService;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetail(Order order, List<BookOrderRequest> items) {
        // TODO 책 가격 검증 필요

        for (BookOrderRequest item : items) {
            Book book = bookService.getBook(item.getBookId());
            OrderDetail orderDetail = new OrderDetail(
                    order,
                    book,
                    book.getSalePrice()
            );
            orderDetailRepository.save(orderDetail);
        }
    }
}
