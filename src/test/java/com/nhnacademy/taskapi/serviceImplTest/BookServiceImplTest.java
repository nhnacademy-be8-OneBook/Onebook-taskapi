package com.nhnacademy.taskapi.serviceImplTest;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.*;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private BookTagRepository bookTagRepository;

    @Mock
    private AladinApiAdapter aladinApiAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBookFromAladin() {
        // Given
        BookSaveDTO dto = new BookSaveDTO();
        dto.setTitle("Sample Book");
        dto.setAuthorName("Sample Author");
        dto.setPubdate("2023-12-01");
        dto.setDescription("Sample Description");
        dto.setIsbn13("1234567890123");
        dto.setPriceSales(1000);
        dto.setPrice(1500);
        dto.setCategoryNames("Fiction>Fantasy");
        dto.setPublisherName("Sample Publisher");
        dto.setSalesPoint(200L);

        when(aladinApiAdapter.fetchAladinData(anyString()))
                .thenReturn("{\"item\":[{" +
                        "\"title\":\"Sample Book\"," +
                        "\"author\":\"Sample Author\"," +
                        "\"pubDate\":\"2023-12-01\"," +
                        "\"description\":\"Sample Description\"," +
                        "\"isbn13\":\"1234567890123\"," +
                        "\"priceSales\":1000," +
                        "\"priceStandard\":1500," +
                        "\"categoryName\":\"Fiction>Fantasy\"," +
                        "\"publisher\":\"Sample Publisher\"," +
                        "\"salesPoint\":200" +
                        "}]}");
        when(bookRepository.findByIsbn13("1234567890123")).thenReturn(null);
        when(publisherRepository.findByName("Sample Publisher")).thenReturn(new Publisher());
        when(authorRepository.findByName("Sample Author")).thenReturn(new Author());
        when(categoryRepository.findByName("Fiction")).thenReturn(new Category());
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        // When
        assertDoesNotThrow(() -> bookService.saveBookFromAladin());  // DTO 전달

        // Then
        verify(aladinApiAdapter, times(1)).fetchAladinData(anyString());
        verify(bookRepository, atLeastOnce()).save(any(Book.class));
    }


    @Test
    void testSaveBook() {
        // Given
        BookSaveDTO dto = new BookSaveDTO();
        dto.setTitle("Sample Book");
        dto.setAuthorName("Sample Author");
        dto.setPubdate("2023-12-01");
        dto.setDescription("Sample Description");
        dto.setIsbn13("1234567890123");
        dto.setPriceSales(1000);
        dto.setPrice(1500);
        dto.setCategoryNames("Fiction>Fantasy");
        dto.setPublisherName("Sample Publisher");
        dto.setSalesPoint(200L);

        when(publisherRepository.findByName("Sample Publisher")).thenReturn(new Publisher());
        when(authorRepository.findByName("Sample Author")).thenReturn(new Author());
        when(categoryRepository.findByName("Fiction")).thenReturn(new Category());
        when(bookRepository.findByIsbn13("1234567890123")).thenReturn(null);

        // When
        Book result = bookService.saveBook(dto);

        // Then
        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
