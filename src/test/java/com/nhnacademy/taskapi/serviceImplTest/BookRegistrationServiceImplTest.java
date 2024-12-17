package com.nhnacademy.taskapi.serviceImplTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

public class BookRegistrationServiceImplTest {

    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private PublisherRepository publisherRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private StockRepository stockRepository;
    @Mock private TagRepository tagRepository;
    @Mock private ImageRepository imageRepository;

    @InjectMocks private BookRegistrationServiceImpl bookRegistrationService;

    private BookSaveDTO bookSaveDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("New Book Title");
        bookSaveDTO.setDescription("New Book Description");
        bookSaveDTO.setIsbn13("1234567890123");
        bookSaveDTO.setPrice(10000);
        bookSaveDTO.setPriceSales(8000);
        bookSaveDTO.setSalesPoint(500L);
        bookSaveDTO.setPubdate("2024-12-15");
        bookSaveDTO.setPublisherName("Publisher Name");
        bookSaveDTO.setCategoryNames("Fiction>Science Fiction");
        bookSaveDTO.setAuthorName("Author Name");
        bookSaveDTO.setTagName("New Tag");
        bookSaveDTO.setImageUrl("https://example.com/image.jpg");
    }

    @Test
    void testSaveBook() {
        // Mock repository methods for saving book
        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Author author = new Author();
        author.setName("Author Name");

        Tag tag = new Tag();
        tag.setName("New Tag");

        Category parentCategory = new Category();
        parentCategory.setName("Fiction");

        Category childCategory = new Category();
        childCategory.setName("Science Fiction");
        childCategory.setParentCategory(parentCategory);

        Stock stock = new Stock();
        stock.setStock(100);

        Image image = new Image();
        image.setImageUrl("https://example.com/image.jpg");

        Book book = new Book();
        book.setTitle("New Book Title");
        book.setDescription("New Book Description");
        book.setIsbn13("1234567890123");
        book.setPrice(10000);
        book.setSalePrice(8000);
        book.setAmount(500L);
        book.setViews(0);
        book.setPubdate(LocalDate.parse("2024-12-15"));

        // Mock behavior
        when(publisherRepository.findByName("Publisher Name")).thenReturn(publisher);
        when(authorRepository.findByName("Author Name")).thenReturn(author);
        when(tagRepository.findByName("New Tag")).thenReturn(tag);
        when(categoryRepository.findByName("Fiction")).thenReturn(null);
        when(categoryRepository.findByName("Science Fiction")).thenReturn(null);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(imageRepository.findByImageUrl(anyString())).thenReturn(java.util.Optional.ofNullable(null));
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        when(stockRepository.findByBook(any(Book.class))).thenReturn(java.util.Optional.ofNullable(null));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        // Perform the save operation
        Book savedBook = bookRegistrationService.saveBook(bookSaveDTO);

        // Verify interactions with repositories
        verify(publisherRepository).findByName("Publisher Name");
        verify(authorRepository).findByName("Author Name");
        verify(tagRepository).findByName("New Tag");
        verify(categoryRepository).findByName("Fiction");
        verify(categoryRepository).findByName("Science Fiction");
        verify(bookRepository).save(any(Book.class));
        verify(imageRepository).save(any(Image.class));
        verify(stockRepository).save(any(Stock.class));

        // Assertions to verify the saved book data
        assertNotNull(savedBook);
        assertEquals("New Book Title", savedBook.getTitle());
        assertEquals("New Book Description", savedBook.getDescription());
        assertEquals("1234567890123", savedBook.getIsbn13());
        assertEquals(10000, savedBook.getPrice());
        assertEquals(8000, savedBook.getSalePrice());
        assertEquals(500L, savedBook.getAmount());
        assertEquals(0, savedBook.getViews());
        assertEquals(LocalDate.parse("2024-12-15"), savedBook.getPubdate());
    }

    @Test
    void testSaveBookWhenAllEntitiesExist() {
        // Existing entities in repository
        Publisher existingPublisher = new Publisher();
        existingPublisher.setName("Publisher Name");

        Author existingAuthor = new Author();
        existingAuthor.setName("Author Name");

        Tag existingTag = new Tag();
        existingTag.setName("New Tag");

        Category existingCategory = new Category();
        existingCategory.setName("Fiction");
        Category existingSubCategory = new Category();
        existingSubCategory.setName("Science Fiction");
        existingSubCategory.setParentCategory(existingCategory);

        // Book and other related entities
        Stock existingStock = new Stock();
        existingStock.setStock(100);

        Image existingImage = new Image();
        existingImage.setImageUrl("https://example.com/image.jpg");

        Book existingBook = new Book();
        existingBook.setTitle("New Book Title");
        existingBook.setDescription("New Book Description");
        existingBook.setIsbn13("1234567890123");
        existingBook.setPrice(10000);
        existingBook.setSalePrice(8000);
        existingBook.setAmount(500L);
        existingBook.setViews(0);
        existingBook.setPubdate(LocalDate.parse("2024-12-15"));

        // Mock repository behavior
        when(publisherRepository.findByName("Publisher Name")).thenReturn(existingPublisher);
        when(authorRepository.findByName("Author Name")).thenReturn(existingAuthor);
        when(tagRepository.findByName("New Tag")).thenReturn(existingTag);
        when(categoryRepository.findByName("Fiction")).thenReturn(existingCategory);
        when(categoryRepository.findByName("Science Fiction")).thenReturn(existingSubCategory);
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);
        when(imageRepository.findByImageUrl(anyString())).thenReturn(java.util.Optional.of(existingImage));
        when(stockRepository.findByBook(any(Book.class))).thenReturn(java.util.Optional.of(existingStock));

        // Perform the save operation
        Book savedBook = bookRegistrationService.saveBook(bookSaveDTO);

        // Verify interactions with repositories
        verify(publisherRepository).findByName("Publisher Name");
        verify(authorRepository).findByName("Author Name");
        verify(tagRepository).findByName("New Tag");
        verify(categoryRepository).findByName("Fiction");
        verify(categoryRepository).findByName("Science Fiction");
        verify(bookRepository).save(any(Book.class));
        verify(imageRepository, never()).save(any(Image.class)); // Should not save image if it already exists
        verify(stockRepository, never()).save(any(Stock.class)); // Should not save stock if it already exists

        // Assertions to verify the saved book data
        assertNotNull(savedBook);
        assertEquals("New Book Title", savedBook.getTitle());
        assertEquals("New Book Description", savedBook.getDescription());
        assertEquals("1234567890123", savedBook.getIsbn13());
        assertEquals(10000, savedBook.getPrice());
        assertEquals(8000, savedBook.getSalePrice());
        assertEquals(500L, savedBook.getAmount());
        assertEquals(0, savedBook.getViews());
        assertEquals(LocalDate.parse("2024-12-15"), savedBook.getPubdate());
    }
}
