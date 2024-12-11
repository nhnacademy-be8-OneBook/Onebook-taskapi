package com.nhnacademy.taskapi.book.repository;

import com.nhnacademy.taskapi.book.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
}
