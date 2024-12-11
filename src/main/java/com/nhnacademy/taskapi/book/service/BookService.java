package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.dto.BookSaveDTO;

public interface BookService {

    //책 등록
    Book SaveBook(BookSaveDTO bookSaveDTO);





}
