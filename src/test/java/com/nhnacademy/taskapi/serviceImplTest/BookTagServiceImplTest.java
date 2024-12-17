package com.nhnacademy.taskapi.serviceImplTest;


import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.exception.TagNotFoundException;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.Impl.BookTagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookTagServiceImplTest {

    @Mock
    private BookTagRepository bookTagRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private BookTagServiceImpl bookTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBookTag_Success() {
        // Given
        long bookId = 1L;
        long tagId = 2L;

        Book book = new Book();
        book.setBookId(bookId);

        Tag tag = new Tag();
        tag.setTagId(tagId);

        BookTag bookTag = new BookTag();
        bookTag.setBook(book);
        bookTag.setTag(tag);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        when(bookTagRepository.save(any(BookTag.class))).thenReturn(bookTag);

        // When
        BookTag result = bookTagService.addBookTag(bookId, tagId);

        // Then
        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(tag, result.getTag());

        verify(bookRepository).findById(bookId);
        verify(tagRepository).findById(tagId);
        verify(bookTagRepository).save(any(BookTag.class));
    }

    @Test
    void addBookTag_BookNotFound() {
        // Given
        long bookId = 1L;
        long tagId = 2L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookTagService.addBookTag(bookId, tagId));

        verify(bookRepository).findById(bookId);
        verifyNoInteractions(tagRepository, bookTagRepository);
    }

    @Test
    void addBookTag_TagNotFound() {
        // Given
        long bookId = 1L;
        long tagId = 2L;

        Book book = new Book();
        book.setBookId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TagNotFoundException.class, () -> bookTagService.addBookTag(bookId, tagId));

        verify(bookRepository).findById(bookId);
        verify(tagRepository).findById(tagId);
        verifyNoInteractions(bookTagRepository);
    }
}
