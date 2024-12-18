package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.exception.TagNotFoundException;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.BookTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookTagServiceImpl implements BookTagService {

    private final BookTagRepository bookTagRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;


    //관리자가 책-태그 등록
    @Override
    @Transactional
    public BookTag addBookTag(long bookId, long tagId) {
        Book book = bookRepository.findById(bookId).orElseThrow( () -> new BookNotFoundException("Book not found !"));

        Tag tag = tagRepository.findById(tagId).orElseThrow( () -> new TagNotFoundException("Tag not found !"));


        BookTag bookTag = new BookTag();

        bookTag.setBook(book);
        bookTag.setTag(tag);
        return bookTagRepository.save(bookTag);
    }
}
