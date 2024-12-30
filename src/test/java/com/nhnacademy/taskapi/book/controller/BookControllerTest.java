package com.nhnacademy.taskapi.book.controller;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.service.BookService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("addBook - 성공")
    void addBook() throws Exception {
        BookSaveDTO saveDTO = new BookSaveDTO();
        saveDTO.setTitle("Test Book");
        saveDTO.setContent("Sample Content");
        saveDTO.setPubdate("2023-12-01");
        saveDTO.setDescription("This is a test book");
        saveDTO.setIsbn13("9781234567890");
        saveDTO.setPriceSales(15000);
        saveDTO.setPrice(20000);
        saveDTO.setCategoryId(1);
        saveDTO.setStock(100);
        saveDTO.setImageName("test-image.jpg");
        saveDTO.setImageBytes("sample image".getBytes());

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Test Book");

        given(bookService.saveBook(ArgumentMatchers.any(BookSaveDTO.class))).willReturn(book);

        mockMvc.perform(post("/task/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "Test Book",
                        "content": "Sample Content",
                        "pubdate": "2023-12-01",
                        "description": "This is a test book",
                        "isbn13": "9781234567890",
                        "priceSales": 15000,
                        "price": 20000,
                        "categoryId": 1,
                        "imageName": "test-image.jpg",
                        "imageBytes": "c2FtcGxlIGltYWdl",
                        "stock": 100
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService).saveBook(ArgumentMatchers.any(BookSaveDTO.class));
    }

    @Test
    @DisplayName("modifyBook - 성공")
    void modifyBook() throws Exception {
        BookUpdateDTO updateDTO = new BookUpdateDTO();
        updateDTO.setTitle("Updated Book");

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("Updated Book");

        given(bookService.updateBook(ArgumentMatchers.eq(1L), ArgumentMatchers.any(BookUpdateDTO.class))).willReturn(book);

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

        verify(bookService).updateBook(ArgumentMatchers.eq(1L), ArgumentMatchers.any(BookUpdateDTO.class));
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

        verify(bookService).bestSellerBooks(ArgumentMatchers.any(PageRequest.class));
    }

    @Test
    @DisplayName("newbooksBook - 성공")
    void newbooksBook() throws Exception {
        Page<Book> books = new PageImpl<>(Collections.singletonList(new Book()));
        given(bookService.newBooks(PageRequest.of(0, 50))).willReturn(books);

        mockMvc.perform(get("/task/book/newbooks")
                        .param("page", "0"))
                .andExpect(status().isOk());

        verify(bookService).newBooks(ArgumentMatchers.any(PageRequest.class));
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