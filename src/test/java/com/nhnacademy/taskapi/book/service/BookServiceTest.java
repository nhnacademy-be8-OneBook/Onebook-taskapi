package com.nhnacademy.taskapi.book.service;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.exception.*;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.service.ImageService;
import com.nhnacademy.taskapi.image.service.Impl.ImageServiceImpl;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.service.Impl.StockServiceImpl;
import com.nhnacademy.taskapi.stock.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @Mock
    private BookAuthorRepository bookAuthorRepository;
    @Mock
    private StockServiceImpl stockService;
    @Mock
    private BookTagRepository bookTagRepository;
    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    @DisplayName("saveBook_Success")
    public void saveBook_Success() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("publisher");

        Author author = new Author();
        author.setAuthorId(1);
        author.setName("author");

        Category category = new Category();
        category.setCategoryId(1);
        category.setName("category");
        category.setParentCategory(null);

        Tag tag = new Tag();
        tag.setName("tag");


        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));

        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("Title");
        bookSaveDTO.setAuthor(author);
        bookSaveDTO.setContent("Content");
        bookSaveDTO.setPubdate(String.valueOf(LocalDate.now()));
        bookSaveDTO.setDescription("Description");
        bookSaveDTO.setIsbn13("1111111111111");
        bookSaveDTO.setPriceSales(10000);
        bookSaveDTO.setPrice(20000);
        bookSaveDTO.setCategoryId(category.getCategoryId());
        bookSaveDTO.setPublisher(publisher);
        bookSaveDTO.setTag(tag);
        bookSaveDTO.setStock(100);
        bookSaveDTO.setImageName("image");
        bookSaveDTO.setImageBytes(new byte[10]);

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Title");
        book.setPrice(20000);
        book.setContent("Content");
        book.setAmount(10);
        book.setDescription("Description");
        book.setPubdate(java.time.LocalDate.now());
        book.setIsbn13("1111111111111");
        book.setSalePrice(10000);
        book.setPublisher(publisher);


        when(bookRepository.save(any())).thenReturn(book);

        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBook(book);
        bookAuthor.setAuthor(author);
        when(bookAuthorRepository.save(any())).thenReturn(bookAuthor);

        BookTag bookTag = new BookTag();
        bookTag.setBook(book);
        bookTag.setTag(tag);
        when(bookTagRepository.save(any())).thenReturn(bookTag);


        BookCategory bookCategory = new BookCategory();
        bookCategory.setBook(book);
        bookCategory.setCategory(category);
        when(bookCategoryRepository.save(any())).thenReturn(bookCategory);

        Stock stock = new Stock();
        stock.setStockId(1L);
        stock.setBook(book);
        stock.setStock(1000);
        when(stockService.addStock(any())).thenReturn(stock);


        Image image = new Image();
        image.setBook(book);
        image.setUrl("test");
        image.setName("image");
        when(imageService.saveImage(any())).thenReturn(image);

        Book result = bookService.saveBook(bookSaveDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("saveBook_Fail_Duplicate")
    public void saveBook_Fail_Duplicate() {
        when(bookRepository.findByIsbn13(anyString())).thenReturn(new Book());

        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setIsbn13("1234567890123");

        assertThrows(BookDuplicateException.class, () -> bookService.saveBook(bookSaveDTO));
    }

    @Test
    @DisplayName("saveBook_Fail_InvalidIsbn")
    public void saveBook_Fail_InvalidIsbn() {
        when(bookRepository.findByIsbn13(anyString())).thenReturn(null);


        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setIsbn13("1234567890");
        assertThrows(InvalidIsbnException.class, () -> bookService.saveBook(bookSaveDTO));
    }

    @Test
    @DisplayName("saveBook_Fail_BookAuthorAlreadyExist")
    public void saveBook_Fail_BookAuthorAlreadyExist() {
        Author author = new Author();
        author.setAuthorId(1);
        author.setName("author");
        BookSaveDTO bookSaveDTO = new BookSaveDTO();

        bookSaveDTO.setIsbn13("1234567890111");
        bookSaveDTO.setPrice(10000);
        bookSaveDTO.setPriceSales(5000);
        bookSaveDTO.setAuthor(author);
        when(bookRepository.save(any())).thenReturn(new Book());
        when(bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(any(Long.class), any(Integer.class))).thenReturn(new BookAuthor());

        assertThrows(BookAuthorAlreadyExistsException.class, () -> bookService.saveBook(bookSaveDTO));
    }

    @Test
    @DisplayName("saveBook_Fail_BookTagAlreadyExist")
    public void saveBook_Fail_BookTagAlreadyExist() {
        // given: 필요한 객체들 초기화
        Author author = new Author();
        author.setAuthorId(1);
        author.setName("author");

        Tag tag = new Tag();
        tag.setTagId(1L); // Tag 객체를 설정

        BookSaveDTO bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setIsbn13("1234567890111");
        bookSaveDTO.setPrice(10000);
        bookSaveDTO.setPriceSales(5000);
        bookSaveDTO.setAuthor(author);
        bookSaveDTO.setTag(tag);  // Tag를 설정

        // Mock 설정
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());
        when(bookTagRepository.findByBook_BookIdAndTag_TagId(any(Long.class), any(Long.class)))
                .thenReturn(new BookTag());  // 이미 BookTag가 존재한다고 가정

        // when & then: 예외가 발생하는지 검증
        assertThrows(BookTagAlreadyExistException.class, () -> bookService.saveBook(bookSaveDTO));

        // 추가: 실제로 bookTagRepository.findByBook_BookIdAndTag_TagId가 호출되었는지 검증
        verify(bookTagRepository, times(1)).findByBook_BookIdAndTag_TagId(any(Long.class), any(Long.class));
    }


    @Test
    @DisplayName("updateBook_Success")
    void updateBook_Success() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("title");

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        BookUpdateDTO dto = new BookUpdateDTO();
        dto.setTitle("test");

        when(bookService.updateBook(1L, dto)).thenReturn(book);

        Book result = bookService.updateBook(1L, dto);

        assertNotNull(result);
        assertEquals("test", result.getTitle());
    }

    @Test
    @DisplayName("deleteBook_Success")
    void deleteBook_Success() {

        Book book = new Book();
        book.setBookId(1L);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));


        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    @DisplayName("Test bestSellerBooks")
    void bestSellerBooks() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsbn13("1234567890123");
        book.setTitle("Test Book");
        book.setAmount(100);
        // given
        List<Book> books = Arrays.asList(book);
        Page<Book> page = new PageImpl<>(books);
        Pageable pageable = mock(Pageable.class);

        // when
        when(bookRepository.findAllByOrderByAmount(pageable)).thenReturn(page);

        // then
        Page<Book> result = bookService.bestSellerBooks(pageable);
        assertEquals(1, result.getTotalElements());  // 1개의 책이 반환되는지 확인
        verify(bookRepository, times(1)).findAllByOrderByAmount(pageable);
    }

    @Test
    @DisplayName("Test newBooks")
    void newBooks() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsbn13("1234567890123");
        book.setTitle("Test Book");
        book.setAmount(100);
        List<Book> books = Arrays.asList(book);
        Page<Book> page = new PageImpl<>(books);
        Pageable pageable = mock(Pageable.class);

        // when
        when(bookRepository.findAllByOrderByPubdate(pageable)).thenReturn(page);

        // then
        Page<Book> result = bookService.newBooks(pageable);
        assertEquals(1, result.getTotalElements());  // 1개의 책이 반환되는지 확인
        verify(bookRepository, times(1)).findAllByOrderByPubdate(pageable);
    }

    @Test
    @DisplayName("Test getBook - Book Found")
    void getBook_BookFound() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsbn13("1234567890123");
        book.setTitle("Test Book");
        book.setAmount(100);
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // when
        Book result = bookService.getBook(1L);

        // then
        assertNotNull(result);
        assertEquals(book.getBookId(), result.getBookId());  // 반환된 책 ID가 일치하는지 확인
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test getBook - Book Not Found")
    void getBook_BookNotFound() {
        Book book = new Book();
        book.setBookId(1L);
        book.setIsbn13("1234567890123");
        book.setTitle("Test Book");
        book.setAmount(100);
        when(bookRepository.existsById(1L)).thenReturn(false);

        // when & then
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(1L));  // 예외가 발생해야 함
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).findById(1L);  // findById는 호출되지 않아야 함
    }
}
