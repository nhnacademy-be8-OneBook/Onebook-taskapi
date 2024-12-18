package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;



}
