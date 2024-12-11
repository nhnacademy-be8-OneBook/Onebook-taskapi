package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book SaveBook(BookSaveDTO bookSaveDTO) {
        Book book = new Book();

        book.setTitle(bookSaveDTO.getTitle());
        book.setContent(bookSaveDTO.getContent());
        book.setDescription(bookSaveDTO.getDescription());
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getSalePrice());
        book.setAmount(bookSaveDTO.getAmount());
        book.setPubdate(bookSaveDTO.getPubDate());

        return bookRepository.save(book);
    }
}
