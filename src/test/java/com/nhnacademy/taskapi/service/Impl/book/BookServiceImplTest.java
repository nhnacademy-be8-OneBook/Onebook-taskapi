package com.nhnacademy.taskapi.service.Impl.book;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBookTest() {
        // Given
        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("Test Title");
        bookSaveDTO.setContent("Test Content");
        bookSaveDTO.setDescription("Test Description");
        bookSaveDTO.setIsbn13("1234567890123");
        bookSaveDTO.setPrice(10000);
        bookSaveDTO.setSalePrice(8000);
        bookSaveDTO.setAmount(10);
        bookSaveDTO.setPubDate(LocalDate.now());

        Book savedBook = new Book();
        savedBook.setBookId(1L);
        savedBook.setTitle("Test Title");
        savedBook.setContent("Test Content");
        savedBook.setDescription("Test Description");
        savedBook.setIsbn13("1234567890123");
        savedBook.setPrice(10000);
        savedBook.setSalePrice(8000);
        savedBook.setAmount(10);
        savedBook.setPubdate(LocalDate.now());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // When
        Book result = bookService.SaveBook(bookSaveDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Title");
        assertThat(result.getContent()).isEqualTo("Test Content");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getIsbn13()).isEqualTo("1234567890123");
        assertThat(result.getPrice()).isEqualTo(10000);
        assertThat(result.getSalePrice()).isEqualTo(8000);
        assertThat(result.getAmount()).isEqualTo(10);
        assertThat(result.getPubdate()).isEqualTo("2024-01-01");
    }
}