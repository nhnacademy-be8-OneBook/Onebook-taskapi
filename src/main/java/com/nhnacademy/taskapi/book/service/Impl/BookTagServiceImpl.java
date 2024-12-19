package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.exception.TagNotFoundException;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.Tag.service.TagService;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.exception.BookTagAlreadyExistException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.book.service.BookTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookTagServiceImpl implements BookTagService {

    private final BookTagRepository bookTagRepository;
    private final BookService bookService;
    private final TagService tagService;


    //관리자가 책-태그 등록
    @Override
    @Transactional
    public BookTag addBookTag(long bookId, long tagId) {
        Book book = bookService.getBook(bookId);

        Tag tag = tagService.getTag(tagId);


        BookTag bookTag = null;
        if(Objects.isNull(bookTagRepository.findByBook_BookIdAndTag_TagId(bookId, tagId))){
            bookTag = new BookTag();
            bookTag.setBook(book);
            bookTag.setTag(tag);
        }else{
            throw new BookTagAlreadyExistException("This Book-Tag Already Exist !");
        }
        return bookTagRepository.save(bookTag);
    }
}
