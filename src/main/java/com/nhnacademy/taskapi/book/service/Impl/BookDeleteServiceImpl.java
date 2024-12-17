package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.book.service.BookDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookDeleteServiceImpl implements BookDeleteService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookTagRepository bookTagRepository;
    private final ImageRepository imageRepository;
    private final StockRepository stockRepository;
    private final PublisherRepository publisherRepository;

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        // 책 조회
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // 관련 이미지 삭제
        imageRepository.findByBook(book).ifPresent(imageRepository::delete);

        // 책 - 카테고리 삭제
        bookCategoryRepository.deleteByBook(book);

        // 책 - 작가 삭제
        bookAuthorRepository.deleteByBook(book);

        // 책 - 태그 삭제
        bookTagRepository.deleteByBook(book);

        // 재고 삭제
        stockRepository.findByBook(book).ifPresent(stockRepository::delete);

        // 책 삭제
        bookRepository.delete(book);

        // 출판사에 연결된 책이 없으면 출판사 삭제 (선택적)
        if (book.getPublisher() != null && book.getPublisher().getBooks().isEmpty()) {
            publisherRepository.delete(book.getPublisher());
        }
    }
}
