package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.category.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BookCategoryController.class)
public class BookCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("createBookCategory - 성공")
    void createBookCategory() throws Exception {
        // Arrange
        Book book = new Book();
        book.setBookId(1L);

        Category category = new Category();
        category.setCategoryId(1);

        BookCategory bookCategory = new BookCategory(1L, book, category);

        BookCategorySaveDTO saveDTO = new BookCategorySaveDTO(1, 1L);

        given(bookCategoryService.save(ArgumentMatchers.any(BookCategorySaveDTO.class)))
                .willReturn(bookCategory);

        // Act & Assert
        mockMvc.perform(post("/task/book/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "categoryId": 1,
                        "bookId": 1
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookCategoryId").value(1))
                .andExpect(jsonPath("$.book.bookId").value(1))
                .andExpect(jsonPath("$.category.categoryId").value(1));

        // Verify
        verify(bookCategoryService).save(ArgumentMatchers.any(BookCategorySaveDTO.class));
    }


}
