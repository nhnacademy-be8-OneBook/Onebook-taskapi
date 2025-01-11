package com.nhnacademy.taskapi.book.controller;

import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.service.BookTagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book/tag")
public class BookTagController {
    private final BookTagService bookTagService;


    @GetMapping("/{bookId}")
    public ResponseEntity<BookTag> getBookTag(@PathVariable long bookId) {
        BookTag bookTag = bookTagService.getBookTag(bookId);
        return ResponseEntity.ok().body(bookTag);
    }
}
