package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.exception.InvalidStockAmountException;
import com.nhnacademy.taskapi.stock.exception.StockDuplicateException;
import com.nhnacademy.taskapi.stock.exception.StockNotFoundException;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.stock.service.Impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Book book;
    private Stock stock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Book 객체 생성
        book = new Book();
        book.setBookId(1L); // 가상의 책 ID 설정

        // Mock Stock 객체 생성
        stock = new Stock();
        stock.setBook(book);
    }

    @Test
    void testAddStock_success() {
        // Arrange
        long bookId = 1L;
        int amount = 10;

        // Mock book 객체를 반환하도록 설정
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // 기존 재고가 없다고 설정
        when(stockRepository.findByBook_bookId(bookId)).thenReturn(null);

        // stockRepository.save() 호출 시, stock 객체를 반환하도록 설정
        Stock stocks = new Stock();
        stocks.setBook(book);
        stocks.setStock(amount);
        when(stockRepository.save(any(Stock.class))).thenReturn(stocks); // 원하는 반환값 설정

        // Act
        Stock addedStock = stockService.addStock(bookId, amount);

        // Assert
        assertNotNull(addedStock); // addedStock가 null이 아닌지 확인
        assertEquals(amount, addedStock.getStock()); // addedStock의 stock 값이 amount와 같아야 한다
        verify(stockRepository, times(1)).save(any(Stock.class)); // save 메소드가 한 번 호출되었는지 확인
    }


    @Test
    void testAddStock_invalidAmount() {
        // Arrange
        long bookId = 1L;
        int invalidAmount = -5;

        // Act & Assert
        InvalidStockAmountException exception = assertThrows(InvalidStockAmountException.class, () -> {
            stockService.addStock(bookId, invalidAmount);
        });
        assertEquals("stock must be greater than 0 ", exception.getMessage());
    }

    @Test
    void testAddStock_stockAlreadyExists() {
        // Arrange
        long bookId = 1L;
        int amount = 10;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(stockRepository.findByBook_bookId(bookId)).thenReturn(stock); // 기존 재고가 이미 있다고 설정

        // Act & Assert
        StockDuplicateException exception = assertThrows(StockDuplicateException.class, () -> {
            stockService.addStock(bookId, amount);
        });
        assertEquals("The stock for the book already exists !", exception.getMessage());
    }

    @Test
    void testUpdateStock_success() {
        // Arrange
        long bookId = 1L;
        int newAmount = 15;

        // Mock stock 객체가 있는 경우
        when(stockRepository.findById(bookId)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock); // save 메소드가 호출될 때, stock 객체를 반환하도록 설정

        // Act
        Stock updatedStock = stockService.updateStock(bookId, newAmount);

        // Assert
        assertNotNull(updatedStock);  // updatedStock가 null이 아니어야 한다
        assertEquals(newAmount, updatedStock.getStock());  // updatedStock의 stock 값이 newAmount와 같아야 한다
        verify(stockRepository, times(1)).save(any(Stock.class));  // save 메소드가 한 번 호출되었는지 확인
    }


    @Test
    void testUpdateStock_notFound() {
        // Arrange
        long bookId = 1L;
        int newAmount = 15;

        when(stockRepository.findById(bookId)).thenReturn(Optional.empty()); // 재고가 없다고 설정

        // Act & Assert
        StockNotFoundException exception = assertThrows(StockNotFoundException.class, () -> {
            stockService.updateStock(bookId, newAmount);
        });
        assertEquals("Stock not found", exception.getMessage());
    }
}
