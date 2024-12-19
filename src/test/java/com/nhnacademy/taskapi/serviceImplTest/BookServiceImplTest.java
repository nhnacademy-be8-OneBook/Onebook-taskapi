//
//package com.nhnacademy.taskapi.serviceImplTest;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.taskapi.Tag.domain.Tag;
//import com.nhnacademy.taskapi.Tag.repository.TagRepository;
//import com.nhnacademy.taskapi.author.domain.Author;
//import com.nhnacademy.taskapi.author.repository.AuthorRepository;
//import com.nhnacademy.taskapi.book.domain.Book;
//import com.nhnacademy.taskapi.book.domain.BookAuthor;
//import com.nhnacademy.taskapi.book.domain.BookCategory;
//import com.nhnacademy.taskapi.book.domain.BookTag;
//import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
//import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
//import com.nhnacademy.taskapi.book.exception.BookDuplicateException;
//import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
//import com.nhnacademy.taskapi.book.repository.*;
//import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
//import com.nhnacademy.taskapi.category.domain.Category;
//import com.nhnacademy.taskapi.category.repository.CategoryRepository;
//import com.nhnacademy.taskapi.image.repository.ImageRepository;
//import com.nhnacademy.taskapi.publisher.domain.Publisher;
//import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
//import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
//import com.nhnacademy.taskapi.stock.domain.Stock;
//import com.nhnacademy.taskapi.stock.repository.StockRepository;
//import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BookServiceImplTest {
//
//    @InjectMocks
//    private BookServiceImpl bookService;
//
//    @Mock
//    private BookRepository bookRepository;
//    @Mock
//    private AuthorRepository authorRepository;
//    @Mock
//    private PublisherRepository publisherRepository;
//    @Mock
//    private BookCategoryRepository bookCategoryRepository;
//    @Mock
//    private BookAuthorRepository bookAuthorRepository;
//    @Mock
//    private CategoryRepository categoryRepository;
//    @Mock
//    private StockRepository stockRepository;
//    @Mock
//    private TagRepository tagRepository;
//    @Mock
//    private ImageRepository imageRepository;
//    @Mock
//    private BookTagRepository bookTagRepository;
//    @Mock
//    private AladinApiAdapter aladinApiAdapter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void saveAladin_ShouldReturnListOfBookSaveDTO() throws JsonProcessingException {
//        // Given
//        String mockResponse = "{ \"item\": [ { \"title\": \"Book Title\", \"author\": \"Author Name\", \"pubDate\": \"2024-01-01\", \"description\": \"Description\", \"isbn13\": \"1234567890\", \"priceSales\": 1000, \"priceStandard\": 1500, \"categoryName\": \"Category\", \"publisher\": \"Publisher\", \"salesPoint\": 100 } ] }";
//        when(aladinApiAdapter.fetchAladinData(anyString())).thenReturn(mockResponse); // API 호출 모킹
//
//        // When
//        List<BookSaveDTO> result = bookService.saveAladin();
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size()); // 하나의 책 정보가 반환되어야 함
//        BookSaveDTO dto = result.get(0);
//        assertEquals("Book Title", dto.getTitle());
//        assertEquals("Author Name", dto.getAuthorName());
//        assertEquals("2024-01-01", dto.getPubdate());
//        assertEquals(1000, dto.getPriceSales());
//    }
//
//
//
//    @Test
//    void saveBook_success() {
//        // Given
//        BookSaveDTO bookSaveDTO = new BookSaveDTO();
//        bookSaveDTO.setTitle("Test Book");
//        bookSaveDTO.setAuthorName("Author Name");
//        bookSaveDTO.setPubdate("2024-01-01");
//        bookSaveDTO.setDescription("Description");
//        bookSaveDTO.setIsbn13("1234567890123");
//        bookSaveDTO.setPriceSales(20000);
//        bookSaveDTO.setPrice(40000);
//        bookSaveDTO.setCategoryNames("Category1111");
//        bookSaveDTO.setPublisherName("Test Publisher");
//        bookSaveDTO.setAuthorName("Test Author");
//
//        when(bookRepository.findByIsbn13(anyString())).thenReturn(null);
//        when(publisherRepository.findByName(anyString())).thenReturn(new Publisher());
//        when(authorRepository.findByName(anyString())).thenReturn(null);
//        when(categoryRepository.findByName(anyString())).thenReturn(null);
//        when(tagRepository.findByNamㅌㅁe(anyString())).thenReturn(null);
//
//        // When
//        Book savedBook = bookService.saveBook(bookSaveDTO);
//
//        // Then
//        assertNotNull(savedBook);
//        verify(bookRepository, times(1)).save(any(Book.class));
//    }
//
//    @Test
//    void saveBook_duplicateIsbn_throwsException() {
//        // Given
//        BookSaveDTO bookSaveDTO = new BookSaveDTO();
//        bookSaveDTO.setIsbn13("1234567890123");
//
//        when(bookRepository.findByIsbn13(anyString())).thenReturn(new Book());
//
//        // When / Then
//        assertThrows(BookDuplicateException.class, () -> bookService.saveBook(bookSaveDTO));
//    }
//
//    @Test
//    void updateBook_success() {
//        // Given
//        Long bookId = 1L;
//        Book book = new Book();
//        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
//        bookUpdateDTO.setTitle("Updated Title");
//        bookUpdateDTO.setContent("Updated Content");
//        bookUpdateDTO.setDescription("Updated Description");
//        bookUpdateDTO.setPrice(1500);
//        bookUpdateDTO.setSalePrice(1000);
//        bookUpdateDTO.setStock(200);
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//        when(bookRepository.save(book)).thenReturn(book);
//        // When
//        Book updatedBook = bookService.updateBook(bookId, bookUpdateDTO);
//
//        // Then
//        assertNotNull(updatedBook);
//        assertEquals("Updated Title", updatedBook.getTitle());
//        verify(bookRepository, times(1)).save(book);
//    }
//
//    @Test
//    void updateBook_notFound_throwsException() {
//        // Given
//        Long bookId = 1L;
//        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
//
//        // When / Then
//        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookId, bookUpdateDTO));
//    }
//
//    @Test
//    void deleteBook_success() {
//        // Given
//        Long bookId = 1L;
//        Book book = new Book();
//
//        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
//
//        // When
//        bookService.deleteBook(bookId);
//
//        // Then
//        verify(bookRepository, times(1)).delete(book);
//    }
//
//    @Test
//    void saveAladin_success() {
//        // Given
//        String response = "{\"item\": [{\"title\": \"Book Title\", \"author\": \"Author Name\", \"pubDate\": \"2024-01-01\", \"description\": \"Description\", \"isbn13\": \"1234567890123\", \"priceSales\": 1000, \"priceStandard\": 1500, \"categoryName\": \"Category\", \"publisher\": \"Publisher Name\", \"salesPoint\": 5000}]}";
//        when(aladinApiAdapter.fetchAladinData(anyString())).thenReturn(response);
//
//        // When
//        List<BookSaveDTO> books = bookService.saveAladin();
//
//        // Then
//        assertNotNull(books);
//        assertEquals(1, books.size());
//        assertEquals("Book Title", books.get(0).getTitle());
//    }
//
//    @Test
//    void bestSellerBooks_success() {
//        // Given
//        Page<Book> bookPage = new PageImpl<>(new ArrayList<>());
//        Pageable pageable = PageRequest.of(0, 10);
//        when(bookRepository.findTop50ByAmount(pageable)).thenReturn(bookPage);
//
//        // When
//        Page<Book> result = bookService.bestSellerBooks(pageable);
//
//        // Then
//        assertNotNull(result);
//        verify(bookRepository, times(1)).findTop50ByAmount(pageable);
//    }
//
//    @Test
//    void saveBook_publisherNotFound_throwsException() {
//        // Given
//        BookSaveDTO bookSaveDTO = new BookSaveDTO();
//        bookSaveDTO.setIsbn13("1234567890123");
//        bookSaveDTO.setPublisherName("Unknown Publisher");
//
//        when(bookRepository.findByIsbn13(anyString())).thenReturn(null);
//        when(publisherRepository.findByName(anyString())).thenReturn(null);  // Publisher not found
//
//        // When / Then
//        assertThrows(PublisherNotFoundException.class, () -> bookService.saveBook(bookSaveDTO));
//    }
//
//}
