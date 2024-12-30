package com.nhnacademy.taskapi.book.controller;

import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book/category")
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    @PostMapping
    public ResponseEntity<?> createBookCategory(@Valid @RequestBody BookCategorySaveDTO bookCategorySaveDTO) {
        BookCategory bookCategory = bookCategoryService.save(bookCategorySaveDTO);
        return ResponseEntity.ok().body(bookCategory);
    }

}
