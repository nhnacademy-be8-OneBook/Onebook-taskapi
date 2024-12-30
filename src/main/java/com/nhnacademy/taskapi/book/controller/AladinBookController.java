package com.nhnacademy.taskapi.book.controller;

import com.nhnacademy.taskapi.book.service.Impl.AladinBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book/aladin")
public class AladinBookController {
    private final AladinBookService aladinBookService;

    @PostMapping
    public ResponseEntity<String> saveBooksFromAladin() {

        aladinBookService.saveBookFromAladin();
        return ResponseEntity.status(HttpStatus.CREATED).body("Books saved successfully.");

    }
}
