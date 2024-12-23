package com.nhnacademy.taskapi.book.repository;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    BookCategory findByBook_bookIdAndCategory_categoryId(Long bookId, Integer categoryId);

    List<BookCategory> findAllByCategory_CategoryId(int categoryId);
}
