package com.nhnacademy.taskapi.book.repository;

import com.nhnacademy.taskapi.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
