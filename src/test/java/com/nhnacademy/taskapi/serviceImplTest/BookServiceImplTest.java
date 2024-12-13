package com.nhnacademy.taskapi.serviceImplTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookAladinDTO;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JsonNode rootNode;

    @Mock
    private JsonNode itemNode;


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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAladin() throws Exception {
        // JSON 응답 시뮬레이션
        String jsonResponse = "{"
                + "\"item\": ["
                + "{"
                + "\"title\": \"Book Title\","
                + "\"author\": \"Author Name\","
                + "\"pubDate\": \"2023-12-12\","
                + "\"description\": \"Book Description\","
                + "\"isbn13\": \"1234567890123\","
                + "\"priceSales\": 15000,"
                + "\"priceStandard\": 20000,"
                + "\"categoryName\": \"Category Name\","
                + "\"publisher\": \"Publisher Name\","
                + "\"salesPoint\": 1000"
                + "}]"
                + "}";

        // Mocking RestTemplate의 동작
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse);

        // Mocking ObjectMapper의 동작
        when(objectMapper.readTree(jsonResponse)).thenReturn(rootNode);
        when(rootNode.path("item")).thenReturn(itemNode);

        // Mocking itemNode의 동작
        JsonNode bookItem = mock(JsonNode.class);
        when(itemNode.iterator()).thenReturn(Collections.singletonList(bookItem).iterator());


        // 서비스 메소드 호출
        List<BookAladinDTO> result = bookService.saveAladin();

        // 검증
        assertNotNull(result);
        assertEquals(7, result.size());  // 리스트에 하나의 책 정보가 있어야 합니다.
    }


    @Test
    @Transactional
    public void testSaveBookFromAladin() {
        // Given: Sample BookAladinDTO list
        BookAladinDTO dto = new BookAladinDTO();
        dto.setPublisherName("Publisher Name");
        dto.setTitle("Book Title");
        dto.setDescription("Book Description");
        dto.setIsbn13("1234567890123");
        dto.setPrice(20000);
        dto.setPriceSales(15000);
        dto.setSalesPoint(1000);
        dto.setCategoryNames("Category1\\u003ECategory2");
        dto.setAuthorName("Author Name");
        dto.setPubdate(LocalDate.now());

        List<BookAladinDTO> dtoList = List.of(dto);

        // Mocking behavior
        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");
        when(publisherRepository.findByName(dto.getPublisherName())).thenReturn(null); // No existing publisher

        Book book = new Book();
        when(bookRepository.findByIsbn13(dto.getIsbn13())).thenReturn(null); // No existing book

        Author author = new Author();
        author.setName(dto.getAuthorName());
        when(authorRepository.findByName(dto.getAuthorName())).thenReturn(null); // No existing author

        Category category = new Category();
        when(categoryRepository.findByName("Category1")).thenReturn(null); // No existing category

        // When: saveBookFromAladin() method is called
        bookService.saveBookFromAladin();

        // Then: Verify interactions
        verify(publisherRepository, times(7)).save(any(Publisher.class));
        verify(bookRepository, times(7)).save(any(Book.class));
        verify(authorRepository, times(7)).save(any(Author.class));
        verify(bookAuthorRepository, times(7)).save(any(BookAuthor.class));
        verify(stockRepository, times(7)).save(any(Stock.class));
        verify(categoryRepository, times(7)).save(any(Category.class));
        verify(bookCategoryRepository, times(7)).save(any(BookCategory.class));
    }
}
