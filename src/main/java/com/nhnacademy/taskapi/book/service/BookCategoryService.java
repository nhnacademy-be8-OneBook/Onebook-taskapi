package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.category.domain.Category;

import java.util.List;

public interface BookCategoryService {
    BookCategory save(BookCategorySaveDTO bookCategorySaveDTO);
    List<BookCategory> getBookByCategory(int categoryId);
}
