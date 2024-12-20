package com.nhnacademy.taskapi.book.service.Impl;


import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookCategoryServiceImpl implements BookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public BookCategory save(BookCategorySaveDTO dto) {
        BookCategory bookCategory = bookCategoryRepository.findByBook_bookIdAndCategory_categoryId(dto.getBookId(),dto.getCategoryId());

        if(Objects.isNull(bookCategory)){
            Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new BookNotFoundException("Book Not Found !"));
            Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category Not Found !"));

            bookCategory = new BookCategory();
            bookCategory.setBook(book);
            bookCategory.setCategory(category);
        }

        return bookCategoryRepository.save(bookCategory);
    }

    @Override
    public List<BookCategory> getBookByCategory(Category category){
        Category cate = categoryRepository.findById(category.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category Not Found !"));

        return bookCategoryRepository.findByCategory(cate);
    }
}
