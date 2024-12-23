package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.exception.AuthorNotFoundException;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.author.service.AuthorService;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.dto.BookAuthorCreateDTO;
import com.nhnacademy.taskapi.book.exception.BookAuthorAlreadyExistsException;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookAuthorService;
import com.nhnacademy.taskapi.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookAuthorServiceImpl implements BookAuthorService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private BookAuthorRepository bookAuthorRepository;


    @Override
    @Transactional
    public BookAuthor createBookAuthor(BookAuthorCreateDTO dto) {
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(()-> new BookNotFoundException("Book Not Found !"));
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException("Author Not Found !"));

        BookAuthor bookAuthor = bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(dto.getBookId(), dto.getAuthorId());

        if(Objects.nonNull(bookAuthor)){
            throw new BookAuthorAlreadyExistsException("BookAuthor Already Exist !");
        }
        bookAuthor = new BookAuthor();
        bookAuthor.setBook(book);
        bookAuthor.setAuthor(author);
        return bookAuthorRepository.save(bookAuthor);
    }
}
