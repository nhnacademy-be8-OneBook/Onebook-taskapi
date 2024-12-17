package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public List<BookSaveDTO> saveAladin();

    //알라딘api - 책 등록
    void saveBookFromAladin();

    //책 등록
    Book saveBook(BookSaveDTO bookSaveDTO);

    // 책 수정
    Book updateBook(Long bookId, BookUpdateDTO bookUpdateDTO);


    // 책 삭제
    void deleteBook(Long bookId);

    // 베스트셀러 목록 조회 (판매량 기준)
    Page<Book> bestSellerBooks(Pageable pageable);
}
