package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.dto.BookAuthorCreateDTO;
import com.nhnacademy.taskapi.book.service.BookAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book/author")
@Slf4j
public class BookAuthorController {
    private final BookAuthorService bookAuthorService;

    @PostMapping
    public ResponseEntity<?> createBookAuthor(@RequestBody @Valid BookAuthorCreateDTO bookAuthorCreateDTO) {
        BookAuthor bookAuthor = bookAuthorService.createBookAuthor(bookAuthorCreateDTO);
        return ResponseEntity.ok().body(bookAuthor);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookAuthor> getBookAuthor(@PathVariable("bookId") Long bookId) {
        BookAuthor bookAuthor = bookAuthorService.getBookAuthorByBookId(bookId);
        return ResponseEntity.ok().body(bookAuthor);
    }
}
