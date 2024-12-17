package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;

public interface BookCategoryService {
    BookCategory save(BookCategorySaveDTO bookCategorySaveDTO);
}
