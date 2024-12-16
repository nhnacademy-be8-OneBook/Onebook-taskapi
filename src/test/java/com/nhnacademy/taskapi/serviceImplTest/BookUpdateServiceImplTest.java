package com.nhnacademy.taskapi.serviceImplTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookUpdateServiceImpl;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.book.repository.*;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

public class BookUpdateServiceImplTest {

    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private PublisherRepository publisherRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private BookCategoryRepository bookCategoryRepository;
    @Mock private BookAuthorRepository bookAuthorRepository;
    @Mock private BookTagRepository bookTagRepository;
    @Mock private TagRepository tagRepository;

    @InjectMocks private BookUpdateServiceImpl bookUpdateService;

    private BookSaveDTO bookSaveDTO;
    private Book book;
    private Author author;
    private Publisher publisher;
    private Tag tag;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        book = new Book();
        book.setBookId(1L);
        book.setTitle("Old Title");

        author = new Author();
        author.setAuthorId(1);
        author.setName("Old Author");

        publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("Old Publisher");

        tag = new Tag();
        tag.setTagId(1L);
        tag.setName("Old Tag");

        category = new Category();
        category.setCategoryId(1);
        category.setName("Old Category");

        bookSaveDTO = new BookSaveDTO();
        bookSaveDTO.setTitle("New Title");
        bookSaveDTO.setDescription("New Description");
        bookSaveDTO.setPrice(5000);
        bookSaveDTO.setPriceSales(4000);
        bookSaveDTO.setSalesPoint(1000L);
        bookSaveDTO.setPubdate("2024-12-15");
        bookSaveDTO.setPublisherName("New Publisher");
        bookSaveDTO.setCategoryNames("New Category");
        bookSaveDTO.setAuthorName("New Author");
        bookSaveDTO.setTagName("New Tag");
    }

    @Test
    void testUpdateBook() {
        // Mock repository methods
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findByName("New Author")).thenReturn(null); // Simulate no author found
        when(publisherRepository.findByName("New Publisher")).thenReturn(publisher);
        when(categoryRepository.findByName("New Category")).thenReturn(null); // Simulate no category found
        when(tagRepository.findByName("New Tag")).thenReturn(null); // Simulate no tag found

        // Mock save method to return the updated book
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Perform the update operation
        Book updatedBook = bookUpdateService.updateBook(1L, bookSaveDTO);

        // Verify interactions with repositories
        verify(bookRepository).findById(1L);
        verify(authorRepository).findByName("New Author");
        verify(publisherRepository).findByName("New Publisher");
        verify(categoryRepository).findByName("New Category");
        verify(tagRepository).findByName("New Tag");
        verify(bookRepository).save(any(Book.class)); // Ensure save is called

        // Assertions to verify the updated book data
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("New Description", updatedBook.getDescription());
        assertEquals(5000, updatedBook.getPrice());
        assertEquals(4000, updatedBook.getSalePrice());
        assertEquals(1000, updatedBook.getAmount());
        assertEquals(LocalDate.parse("2024-12-15"), updatedBook.getPubdate());
    }
}
