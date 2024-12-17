package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookDeleteServiceImpl;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookDeleteServiceImplTest {

    @Mock private BookRepository bookRepository;
    @Mock private BookCategoryRepository bookCategoryRepository;
    @Mock private BookAuthorRepository bookAuthorRepository;
    @Mock private BookTagRepository bookTagRepository;
    @Mock private ImageRepository imageRepository;
    @Mock private StockRepository stockRepository;
    @Mock private PublisherRepository publisherRepository;

    @InjectMocks private BookDeleteServiceImpl bookDeleteService;

    private Book book;
    private Publisher publisher;
    private Author author;
    private Tag tag;
    private Category category;
    private Stock stock;
    private Image image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock data setup
        book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");

        publisher = mock(Publisher.class);  // Mock Publisher here
        publisher.setPublisherId(1L);
        publisher.setName("Test Publisher");

        author = new Author();
        author.setAuthorId(1);
        author.setName("Test Author");

        tag = new Tag();
        tag.setTagId(1L);
        tag.setName("Test Tag");

        category = new Category();
        category.setCategoryId(1);
        category.setName("Test Category");

        stock = new Stock();
        stock.setStockId(1L);
        stock.setStock(100);
        stock.setBook(book);

        image = new Image();
        image.setImageId(1L);
        image.setImageUrl("testImage.jpg");
        image.setBook(book);

        // Make sure that the book's publisher is the mocked publisher
        book.setPublisher(publisher);
    }

    @Test
    void testDeleteBook() {
        // Mock repository methods
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(imageRepository.findByBook(book)).thenReturn(Optional.of(image));
        when(stockRepository.findByBook(book)).thenReturn(Optional.of(stock));

        // Stub the getBooks() method of the mocked Publisher to return an empty list
        when(publisher.getBooks()).thenReturn(new ArrayList<>()); // No books associated with this publisher

        // When: deleteBook is called
        bookDeleteService.deleteBook(1L);

        // Verify that the book and all related entities are deleted
        verify(imageRepository).delete(image);
        verify(bookCategoryRepository).deleteByBook(book);
        verify(bookAuthorRepository).deleteByBook(book);
        verify(bookTagRepository).deleteByBook(book);
        verify(stockRepository).delete(stock);
        verify(bookRepository).delete(book);

        // Verify if publisher is deleted when no books are left
        verify(publisherRepository).delete(publisher);
    }

    @Test
    void testDeleteBookWithoutPublisherDeletion() {
        // Set up the book with no publisher
        book.setPublisher(null);

        // Mock repository methods
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(imageRepository.findByBook(book)).thenReturn(Optional.of(image));
        when(stockRepository.findByBook(book)).thenReturn(Optional.of(stock));

        // When: deleteBook is called
        bookDeleteService.deleteBook(1L);

        // Verify that the book and all related entities are deleted
        verify(imageRepository).delete(image);
        verify(bookCategoryRepository).deleteByBook(book);
        verify(bookAuthorRepository).deleteByBook(book);
        verify(bookTagRepository).deleteByBook(book);
        verify(stockRepository).delete(stock);
        verify(bookRepository).delete(book);

        // Verify that publisher is not deleted (because there is no publisher)
        verify(publisherRepository, never()).delete(any(Publisher.class));
    }

    @Test
    void testDeleteBookWhenNotFound() {
        // Mock bookRepository to return empty (book not found)
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When: deleteBook is called
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookDeleteService.deleteBook(1L);
        });

        // Then: Ensure that "Book not found" exception is thrown
        assertEquals("Book not found", exception.getMessage());
    }
}