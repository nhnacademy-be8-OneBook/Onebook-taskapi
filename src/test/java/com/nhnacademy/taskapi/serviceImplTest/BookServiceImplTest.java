package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @InjectMocks
    private BookServiceImpl bookService; // BookService의 구현체

    @Test
    void testSaveBookFromAladin() throws Exception {
        // 1. Mock API 응답 설정
        String mockApiResponse = """
        {
            "item": [
                {
                    "title": "Sample Book",
                    "author": "John Doe",
                    "pubDate": "2024년 11월 01일",
                    "description": "A test book",
                    "isbn13": "1234567890123",
                    "priceSales": 20000,
                    "priceStandard": 25000,
                    "categoryName": "Fiction\\u003EAdventure",
                    "publisher": "Sample Publisher",
                    "salesPoint": 500
                }
            ]
        }
        """;

        // 2. Mock 레포지토리 동작 설정
        when(publisherRepository.findByName("Sample Publisher")).thenReturn(null);
        when(bookRepository.findByIsbn13("1234567890123")).thenReturn(null);
        when(authorRepository.findByName("John Doe")).thenReturn(null);
        when(categoryRepository.findByName("Fiction")).thenReturn(null);
        when(categoryRepository.findByName("Adventure")).thenReturn(null);

        // 3. Mock API 호출 대신 RestTemplate을 대체
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockApiResponse);

        // 4. 메서드 실행
        bookService.saveBookFromAladin();

        // 5. 검증
        verify(publisherRepository, times(50)).save(any(Publisher.class));
        verify(publisherRepository), ti.save(any(Publisher.class));
        verify(bookRepository).save(any(Book.class));
        verify(authorRepository).save(any(Author.class));
        verify(bookAuthorRepository).save(any(BookAuthor.class));
        verify(stockRepository).save(any(Stock.class));
        verify(categoryRepository, times(2)).save(any(Category.class));
        verify(bookCategoryRepository).save(any(BookCategory.class));
    }

}