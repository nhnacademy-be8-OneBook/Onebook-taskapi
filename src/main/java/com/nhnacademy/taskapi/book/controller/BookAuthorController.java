package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.dto.BookAuthorCreateDTO;
import com.nhnacademy.taskapi.book.service.BookAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book/author")
public class BookAuthorController {
    private final BookAuthorService bookAuthorService;

    @PostMapping
    public ResponseEntity<?> createBookAuthor(@RequestBody @Valid BookAuthorCreateDTO bookAuthorCreateDTO) {
        BookAuthor bookAuthor = bookAuthorService.createBookAuthor(bookAuthorCreateDTO);
        return ResponseEntity.ok().body(bookAuthor);
    }

}
