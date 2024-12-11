package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.dto.BookSaveDTO;

public interface BookService {

    //알라딘api - 책 등록
    void saveBookFromAladin();


    //책 등록
    Book saveBook(BookSaveDTO bookSaveDTO);





}
