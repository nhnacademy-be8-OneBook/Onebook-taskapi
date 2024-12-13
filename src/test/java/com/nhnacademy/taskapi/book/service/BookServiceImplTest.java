package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private BookAuthorRepository bookAuthorRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookTagRepository bookTagRepository;
    @Mock
    private StockRepository stockRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookServiceImpl bookService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void name() {

        when(publisherRepository.findByName("test")).thenReturn(new Publisher(1L, "test"));

        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setPublisherName("test");

        Book book = bookService.saveBook(bookSaveDTO);

        Assertions.assertNotNull(book);

    }

}
