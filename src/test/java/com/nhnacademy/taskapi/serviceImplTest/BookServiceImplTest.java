//package com.nhnacademy.taskapi.serviceImplTest;
//import com.nhnacademy.taskapi.book.domain.Book;
//import com.nhnacademy.taskapi.book.repository.*;
//import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
//import com.nhnacademy.taskapi.dto.BookSaveDTO;
//import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
//import com.nhnacademy.taskapi.publisher.domain.Publisher;
//import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
//import com.nhnacademy.taskapi.author.domain.Author;
//import com.nhnacademy.taskapi.author.repository.AuthorRepository;
//import com.nhnacademy.taskapi.category.domain.Category;
//import com.nhnacademy.taskapi.category.repository.CategoryRepository;
//import com.nhnacademy.taskapi.image.domain.Image;
//import com.nhnacademy.taskapi.image.repository.ImageRepository;
//import com.nhnacademy.taskapi.stock.domain.Stock;
//import com.nhnacademy.taskapi.stock.repository.StockRepository;
//import com.nhnacademy.taskapi.Tag.domain.Tag;
//import com.nhnacademy.taskapi.Tag.repository.TagRepository;
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
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//class BookServiceImplTest {
//
//    @InjectMocks
//    private BookServiceImpl bookService;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private AuthorRepository authorRepository;
//
//    @Mock
//    private PublisherRepository publisherRepository;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private StockRepository stockRepository;
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @Mock
//    private ImageRepository imageRepository;
//
//    @Mock
//    private BookCategoryRepository bookCategoryRepository;
//
//    @Mock
//    private BookAuthorRepository bookAuthorRepository;
//
//    @Mock
//    private BookTagRepository bookTagRepository;
//
//    @Mock
//    private AladinApiAdapter aladinApiAdapter;
//
//    private Pageable pageable;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        pageable = PageRequest.of(0, 50);
//    }
//
//    @Test
//    void testSaveBookFromAladin() {
//        // Given
//        BookSaveDTO dto = new BookSaveDTO();
//        dto.setTitle("Sample Book");
//        dto.setAuthorName("Sample Author");
//        dto.setPubdate("2023-12-01");
//        dto.setDescription("Sample Description");
//        dto.setIsbn13("1234567890123");
//        dto.setPriceSales(1000);
//        dto.setPrice(1500);
//        dto.setCategoryNames("Fiction>Fantasy");
//        dto.setPublisherName("Sample Publisher");
//        dto.setSalesPoint(200L);
//
//        when(aladinApiAdapter.fetchAladinData(anyString()))
//                .thenReturn("{\"item\":[{" +
//                        "\"title\":\"Sample Book\"," +
//                        "\"author\":\"Sample Author\"," +
//                        "\"pubDate\":\"2023-12-01\"," +
//                        "\"description\":\"Sample Description\"," +
//                        "\"isbn13\":\"1234567890123\"," +
//                        "\"priceSales\":1000," +
//                        "\"priceStandard\":1500," +
//                        "\"categoryName\":\"Fiction>Fantasy\"," +
//                        "\"publisher\":\"Sample Publisher\"," +
//                        "\"salesPoint\":200" +
//                        "}]}");
//        when(bookRepository.findByIsbn13("1234567890123")).thenReturn(null);
//        when(publisherRepository.findByName("Sample Publisher")).thenReturn(new Publisher());
//        when(authorRepository.findByName("Sample Author")).thenReturn(new Author());
//        when(categoryRepository.findByName("Fiction")).thenReturn(new Category());
//        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
//        // When
//        assertDoesNotThrow(() -> bookService.saveBookFromAladin());  // DTO 전달
//
//        // Then
//        verify(aladinApiAdapter, times(1)).fetchAladinData(anyString());
//        verify(bookRepository, atLeastOnce()).save(any(Book.class));
//    }
//
//
//    @Test
//    void testSaveBook() {
//        // Given
//        BookSaveDTO dto = new BookSaveDTO();
//        dto.setTitle("Sample Book");
//        dto.setAuthorName("Sample Author");
//        dto.setPubdate("2023-12-01");
//        dto.setDescription("Sample Description");
//        dto.setIsbn13("1234567890123");
//        dto.setPriceSales(1000);
//        dto.setPrice(1500);
//        dto.setCategoryNames("Fiction>Fantasy");
//        dto.setPublisherName("Sample Publisher");
//        dto.setSalesPoint(200L);
//
//        when(publisherRepository.findByName("Sample Publisher")).thenReturn(new Publisher());
//        when(authorRepository.findByName("Sample Author")).thenReturn(new Author());
//        when(categoryRepository.findByName("Fiction")).thenReturn(new Category());
//        when(bookRepository.findByIsbn13("1234567890123")).thenReturn(null);
//
//        // When
//        Book result = bookService.saveBook(dto);
//
//        // Then
//        assertNotNull(result);
//        verify(bookRepository, times(1)).save(any(Book.class));
//    }
//
//    @Test
//    void testBestSellerBooks() {
//        // 데이터 준비 (세터를 통해 값 설정)
//        Book book1 = new Book();
//        book1.setBookId(1L);
//        book1.setTitle("Book 1");
//        book1.setDescription("Description of Book 1");
//        book1.setIsbn13("1234567890123");
//        book1.setPrice(500);
//        book1.setSalePrice(450);
//        book1.setAmount(1000);
//        book1.setViews(200);
//        book1.setPubdate(LocalDate.of(2023, 1, 1));
//
//        Book book2 = new Book();
//        book2.setBookId(2L);
//        book2.setTitle("Book 2");
//        book2.setDescription("Description of Book 2");
//        book2.setIsbn13("1234567890456");
//        book2.setPrice(600);
//        book2.setSalePrice(550);
//        book2.setAmount(800);
//        book2.setViews(150);
//        book2.setPubdate(LocalDate.of(2023, 1, 2));
//
//        List<Book> books = Arrays.asList(book1, book2);
//
//        // Page<Book> 객체 생성
//        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());
//
//        // Mocking bookRepository.findTop50ByAmount() 메서드
//        when(bookRepository.findTop50ByAmount(pageable)).thenReturn(bookPage);
//
//        // 메서드 호출
//        Page<Book> result = bookService.bestSellerBooks(pageable);
//
//        // 결과 검증
//        assertNotNull(result); // 결과가 null이 아니어야 합니다
//        assertEquals(2, result.getTotalElements()); // 책 2개
//        assertEquals(1, result.getTotalPages()); // 총 페이지 수는 1페이지
//        assertEquals("Book 1", result.getContent().get(0).getTitle()); // 첫 번째 책 제목 확인
//        assertEquals(1000, result.getContent().get(0).getAmount()); // 첫 번째 책의 판매량 확인
//
//        // 메서드 호출 검증
//        verify(bookRepository, times(1)).findTop50ByAmount(pageable);
//    }
//}
