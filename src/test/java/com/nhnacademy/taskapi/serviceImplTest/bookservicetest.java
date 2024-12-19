package com.nhnacademy.taskapi.serviceImplTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.exception.BookDuplicateException;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class bookservicetest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookAuthorRepository bookAuthorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private BookTagRepository bookTagRepository;
    @Mock
    private AladinApiAdapter aladinApiAdapter;

    private BookSaveDTO bookSaveDTO;
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("Book Title");
        bookSaveDTO.setIsbn13("1234567890123");
        bookSaveDTO.setPublisherName("Publisher A");
        bookSaveDTO.setPrice(1500);
        bookSaveDTO.setPriceSales(1000);
        bookSaveDTO.setPubdate("2023-01-01");

        publisher = new Publisher();
        publisher.setName("Publisher A");
    }


    @Test
    void saveAladin_ShouldReturnListOfBookSaveDTO() throws JsonProcessingException {
        // Given
        String mockResponse = "{ \"item\": [ { \"title\": \"Book Title\", \"author\": \"Author Name\", \"pubDate\": \"2024-01-01\", \"description\": \"Description\", \"isbn13\": \"1234567890\", \"priceSales\": 1000, \"priceStandard\": 1500, \"categoryName\": \"Category\", \"publisher\": \"Publisher\", \"salesPoint\": 100 } ] }";
        when(aladinApiAdapter.fetchAladinData(anyString())).thenReturn(mockResponse); // API 호출 모킹

        // When
        List<BookSaveDTO> result = bookService.saveAladin();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size()); // 하나의 책 정보가 반환되어야 함
        BookSaveDTO dto = result.get(0);
        assertEquals("Book Title", dto.getTitle());
        assertEquals("Author Name", dto.getAuthorName());
        assertEquals("2024-01-01", dto.getPubdate());
        assertEquals(1000, dto.getPriceSales());
    }

    @Test
    @DisplayName("saveBookFromAladin: Aladin API 데이터를 통해 책 저장 호출 테스트")
    void testSaveBookFromAladinSuccess() {
        // Given
        BookSaveDTO book1 = new BookSaveDTO();
        book1.setTitle("Book Title 1");
        book1.setAuthorName("Author 1");
        book1.setIsbn13("1234567890123");

        BookSaveDTO book2 = new BookSaveDTO();
        book2.setTitle("Book Title 2");
        book2.setAuthorName("Author 2");
        book2.setIsbn13("9876543210987");

        List<BookSaveDTO> bookSaveDTOList = List.of(book1, book2);

        // saveAladin() Mocking: DTO 목록 반환
        when(bookService.saveAladin()).thenReturn(bookSaveDTOList);

        // saveBook() Mocking: 각 BookSaveDTO 저장 처리
        doNothing().when(bookService).saveBook(any(BookSaveDTO.class));

        // When
        bookService.saveBookFromAladin();

        // Then
        // saveAladin 호출 여부 확인
        verify(bookService, times(1)).saveAladin();

        // saveBook 호출 여부 확인 (각 DTO에 대해 한 번씩 호출)
        verify(bookService, times(1)).saveBook(book1);
        verify(bookService, times(1)).saveBook(book2);
    }

    @Test
    @DisplayName("saveBook: 책 저장 성공 테스트")
    void testSaveBookSuccess() {
        // Given
        when(publisherRepository.findByName(anyString())).thenReturn(publisher);
        when(bookRepository.findByIsbn13(anyString())).thenReturn(null);

        // When
        Book savedBook = bookService.saveBook(bookSaveDTO);

        // Then
        assertNotNull(savedBook);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(publisherRepository, times(1)).findByName("Publisher A");
    }

    @Test
    @DisplayName("saveBook: 이미 존재하는 책 저장 시 예외 발생 테스트")
    void testSaveBookDuplicateException() {
        // Given
        when(bookRepository.findByIsbn13(anyString())).thenReturn(new Book());

        // When & Then
        assertThrows(BookDuplicateException.class, () -> bookService.saveBook(bookSaveDTO));
        verify(bookRepository, times(1)).findByIsbn13("1234567890123");
    }

    @Test
    @DisplayName("saveBook: 잘못된 출판사 이름 예외 발생 테스트")
    void testSaveBookPublisherNotFoundException() {
        // Given
        when(publisherRepository.findByName(anyString())).thenReturn(null);

        // When & Then
        assertThrows(PublisherNotFoundException.class, () -> bookService.saveBook(bookSaveDTO));
    }

    @Test
    @DisplayName("getBook: 책 조회 성공 테스트")
    void testGetBookSuccess() {
        // Given
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Book Title");
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        // When
        Book foundBook = bookService.getBook(1L);

        // Then
        assertNotNull(foundBook);
        assertEquals("Book Title", foundBook.getTitle());
        verify(bookRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("getBook: 존재하지 않는 책 조회 시 예외 발생 테스트")
    void testGetBookNotFoundException() {
        // Given
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(1L));
        verify(bookRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("deleteBook: 책 삭제 성공 테스트")
    void testDeleteBookSuccess() {
        // Given
        Book book = new Book();
        book.setBookId(1L);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    @DisplayName("deleteBook: 존재하지 않는 책 삭제 시 예외 발생 테스트")
    void testDeleteBookNotFoundException() {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("bestSellerBooks: 베스트셀러 책 목록 조회 테스트")
    void testBestSellerBooks() {
        // Given
        Page<Book> mockPage = mock(Page.class);
        when(bookRepository.findTop50ByAmount(any(Pageable.class))).thenReturn(mockPage);

        // When
        Page<Book> bestSellers = bookService.bestSellerBooks(mock(Pageable.class));

        // Then
        assertNotNull(bestSellers);
        assertTrue(bestSellers.getContent().isEmpty());
        verify(bookRepository, times(1)).findTop50ByAmount(any(Pageable.class));
    }

    @Test
    @DisplayName("updateBook: 책 정보 수정 성공 테스트")
    void testUpdateBookSuccess() {
        // Given
        BookUpdateDTO updateDTO = new BookUpdateDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setContent("Updated contetnt");
        updateDTO.setDescription("updated description");
        updateDTO.setPrice(2000);
        updateDTO.setSalePrice(1500);
        updateDTO.setStock(100);

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Original Title");
        book.setContent("content1");
        book.setDescription("description1");
        book.setPrice(1000);
        book.setSalePrice(200);

        book.setTitle(updateDTO.getTitle());

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        // When
        Book updatedBook = bookService.updateBook(1L, updateDTO);

        // Then
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("updateBook: 존재하지 않는 책 수정 시 예외 발생 테스트")
    void testUpdateBookNotFoundException() {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, new BookUpdateDTO()));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("newBooks: 최신 책 목록 조회 테스트")
    void testNewBooksSuccess() {
        // Given
        Book book1 = new Book();
        book1.setTitle("Book 1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        book1.setPubdate(LocalDate.parse("2023-12-01", formatter));

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPubdate(LocalDate.parse("2023-11-25", formatter));


        List<Book> bookList = List.of(book1, book2);
        Page<Book> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(bookList);
        when(bookRepository.findAllByOrderByPubdate(any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = mock(Pageable.class);

        // When
        Page<Book> result = bookService.newBooks(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        assertEquals("Book 1", result.getContent().get(0).getTitle());
        assertEquals(LocalDate.parse("2023-12-01", formatter), result.getContent().get(0).getPubdate());

        assertEquals("Book 2", result.getContent().get(1).getTitle());
        assertEquals(LocalDate.parse("2023-11-25", formatter), result.getContent().get(1).getPubdate());

        verify(bookRepository, times(1)).findAllByOrderByPubdate(any(Pageable.class));
    }


}