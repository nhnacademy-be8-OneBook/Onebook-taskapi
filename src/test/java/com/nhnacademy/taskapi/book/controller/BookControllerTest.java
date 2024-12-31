package com.nhnacademy.taskapi.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("addBook - 성공")
    void addBook() throws Exception {
        BookSaveDTO bookSaveDTO = new BookSaveDTO();

        Author author = new Author();
        author.setName("Test Author");

        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setPublisherId(1L);

        Tag tag = new Tag();
        tag.setName("Test Tag");
        tag.setTagId(1L);

        author.setAuthorId(1);
        bookSaveDTO.setTitle("Test Book");
        bookSaveDTO.setAuthor(author);
        bookSaveDTO.setContent("This is a test book.");
        bookSaveDTO.setPubdate("2024-01-01");
        bookSaveDTO.setDescription("Test Description");
        bookSaveDTO.setIsbn13("1234567890123");
        bookSaveDTO.setPriceSales(1500);
        bookSaveDTO.setPrice(2000);
        bookSaveDTO.setCategoryId(1);
        bookSaveDTO.setPublisher(publisher);
        bookSaveDTO.setTag(tag);
        bookSaveDTO.setStock(10);
        MockMultipartFile imageFile = new MockMultipartFile("image", "test_image.jpg", "image/jpeg", "dummy image content".getBytes());


        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");

        when(bookService.saveBook(any(BookSaveDTO.class), any(MultipartFile.class))).thenReturn(book);


        String dto = objectMapper.writeValueAsString(bookSaveDTO);
        // HTTP POST 요청 시뮬레이션
        mockMvc.perform(multipart("/task/book")
                        .file("image", imageFile.getBytes())
                        .file(new MockMultipartFile("dto", "","application/json", dto.getBytes()))  // dto는 JSON 형식으로 전달
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())  // HTTP 상태 코드 201 CREATED 검증
                .andExpect(jsonPath("$.bookId").value(1))  // 반환된 Book 객체의 bookId 검증
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    @DisplayName("modifyBook - 성공")
    void modifyBook() throws Exception {
        BookUpdateDTO updateDTO = new BookUpdateDTO();
        updateDTO.setTitle("Updated Book");

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Updated Book");

        given(bookService.updateBook(ArgumentMatchers.eq(1L), any(BookUpdateDTO.class))).willReturn(book);

        mockMvc.perform(put("/task/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "Updated Book"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Updated Book"));

        verify(bookService).updateBook(ArgumentMatchers.eq(1L), any(BookUpdateDTO.class));
    }

    @Test
    @DisplayName("deleteBook - 성공")
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/task/book/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }

    @Test
    @DisplayName("bestsellersBook - 성공")
    void bestsellersBook() throws Exception {
        Page<Book> books = new PageImpl<>(Collections.singletonList(new Book()));
        given(bookService.bestSellerBooks(PageRequest.of(0, 20))).willReturn(books);

        mockMvc.perform(get("/task/book/bestsellers")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk());

        verify(bookService).bestSellerBooks(any(PageRequest.class));
    }

    @Test
    @DisplayName("newbooksBook - 성공")
    void newbooksBook() throws Exception {
        Page<Book> books = new PageImpl<>(Collections.singletonList(new Book()));
        given(bookService.newBooks(PageRequest.of(0, 50))).willReturn(books);

        mockMvc.perform(get("/task/book/newbooks")
                        .param("page", "0"))
                .andExpect(status().isOk());

        verify(bookService).newBooks(any(PageRequest.class));
    }

    @Test
    @DisplayName("getBook - 성공")
    void getBook() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");

        given(bookService.getBook(1L)).willReturn(book);

        mockMvc.perform(get("/task/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService).getBook(1L);
    }
}