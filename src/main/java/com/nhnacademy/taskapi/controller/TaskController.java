package com.nhnacademy.taskapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


// 추가하면 되는 방식?
@RestController
public class TaskController {

    @GetMapping("/task/hello")
    public String hello() {
        return "TASK-SERVICE";
    }

    // ex) 상품 목록 조회
    @GetMapping("/task/books")
    public String getBooks() {
        return "List of books...";
    }

    // ex) 주문 처리
    @GetMapping("/task/order/{bookId}")
    public String orderBook(@PathVariable("bookId") String bookId) {
        return "Order placed for book: " + bookId;
    }
}