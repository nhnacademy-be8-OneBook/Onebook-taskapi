package com.nhnacademy.taskapi.book.service.Impl;


import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookCategorySaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {
    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookCategory save(BookCategorySaveDTO bookCategorySaveDTO) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCategory(categoryRepository.findById(bookCategorySaveDTO.getCategoryId()).orElse(null));
        bookCategory.setBook(bookRepository.findById(bookCategorySaveDTO.getBookId()).orElse(null));
        return bookCategoryRepository.save(bookCategory);
    }
}
