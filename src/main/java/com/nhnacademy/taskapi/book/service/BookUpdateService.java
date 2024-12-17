package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.dto.BookSaveDTO;

public interface BookUpdateService {

    // 책 수정
    Book updateBook(Long bookId, BookSaveDTO bookSaveDTO);
}
