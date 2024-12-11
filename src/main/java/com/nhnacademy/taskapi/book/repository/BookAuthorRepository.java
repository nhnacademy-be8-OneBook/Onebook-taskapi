package com.nhnacademy.taskapi.book.repository;

import com.nhnacademy.taskapi.book.domain.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Integer> {
}
