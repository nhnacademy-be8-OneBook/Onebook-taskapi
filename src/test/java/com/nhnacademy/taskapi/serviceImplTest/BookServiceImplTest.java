package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AladinApiAdapter aladinApiAdapter;

    @Mock
    private ImageRepository imageRepository; // Mock ImageRepository

    @Mock
    private CategoryRepository categoryRepository; // Mock CategoryRepository

    @InjectMocks
    private BookServiceImpl bookService;

    private BookSaveDTO bookSaveDTO;
    private Publisher publisher;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup Publisher
        publisher = new Publisher();
        publisher.setName("Test Publisher");

        // Setup BookSaveDTO
        bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("Test Book");
        bookSaveDTO.setAuthorName("Test Author");
        bookSaveDTO.setPubdate("2023-12-15");
        bookSaveDTO.setIsbn13("1234567890");
        bookSaveDTO.setPrice(15000);
        bookSaveDTO.setPriceSales(13000);
        bookSaveDTO.setSalesPoint(5000L);
        bookSaveDTO.setCategoryNames("Fiction>Science");
        bookSaveDTO.setPublisherName("Test Publisher");
        bookSaveDTO.setDescription("Test Description");
        bookSaveDTO.setTagName("Best Seller");

        // Setup Book entity
        book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");
        book.setDescription("Test Description");
        book.setIsbn13("1234567890");
        book.setPrice(15000);
        book.setSalePrice(13000);
        book.setAmount(5000L);
        book.setViews(100L);
        book.setPubdate(LocalDate.parse("2023-12-15"));
        book.setPublisher(publisher);
    }

    @Test
    void saveAladin_ShouldReturnBookSaveDTOList() {
        // Given: Mock Aladin API response
        String apiResponse = "[{\"title\":\"Test Book\",\"author\":\"Test Author\",\"pubDate\":\"2023-12-15\",\"isbn13\":\"1234567890\",\"priceSales\":13000,\"priceStandard\":15000,\"categoryName\":\"Fiction>Science\",\"publisher\":\"Test Publisher\",\"salesPoint\":5000}]";

        when(aladinApiAdapter.fetchAladinData(any())).thenReturn(apiResponse);

        // When: Call saveAladin()
        var result = bookService.saveAladin();

        // Then: Verify correct data is parsed into BookSaveDTO
        verify(aladinApiAdapter, times(1)).fetchAladinData(any());
        assert(result.size() > 0);
        assert(result.get(0).getTitle().equals("Test Book"));
        assert(result.get(0).getAuthorName().equals("Test Author"));
    }

    @Test
    void saveBookFromAladin_ShouldSaveBooks() {
        // Given: Mock the Aladin API and saveBook behavior
        String apiResponse = "[{\"title\":\"Test Book\",\"author\":\"Test Author\",\"pubDate\":\"2023-12-15\",\"isbn13\":\"1234567890\",\"priceSales\":13000,\"priceStandard\":15000,\"categoryName\":\"Fiction>Science\",\"publisher\":\"Test Publisher\",\"salesPoint\":5000}]";
        when(aladinApiAdapter.fetchAladinData(any())).thenReturn(apiResponse);
        when(bookRepository.save(any())).thenReturn(book);

        // When: Call saveBookFromAladin
        bookService.saveBookFromAladin();

        // Then: Verify that saveBook() was called
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void saveOrUpdateBook_ShouldThrowPublisherNotFoundException() {
        // Given: Mock publisherRepository to return null (publisher not found)
        when(publisherRepository.findByName(any())).thenReturn(null);

        // When & Then: Assert that exception is thrown
        try {
            bookService.saveOrUpdateBook(null, bookSaveDTO);
        } catch (PublisherNotFoundException e) {
            assert(e.getMessage().equals("Publisher not found!"));
        }
    }

    @Test
    void deleteBook_ShouldThrowBookNotFoundException() {
        // Given: Mock bookRepository to return empty
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then: Assert that exception is thrown
        try {
            bookService.deleteBook(1L);
        } catch (RuntimeException e) {
            assert(e.getMessage().equals("Book not found"));
        }
    }

    @Test
    void bestSellerBooks_ShouldReturnBooks() {
        // Given: Mock the bookRepository to return a page of books
        var pageable = Mockito.mock(Pageable.class);
        var bookPage = Mockito.mock(Page.class);
        when(bookRepository.findTop50ByAmount(pageable)).thenReturn(bookPage);

        // When: Call bestSellerBooks
        Page<Book> result = bookService.bestSellerBooks(pageable);

        // Then: Verify that bookRepository.findTop50ByAmount() was called
        verify(bookRepository, times(1)).findTop50ByAmount(pageable);
    }
}
