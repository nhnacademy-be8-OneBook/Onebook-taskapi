package com.nhnacademy.taskapi.author.controller;


import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.dto.AuthorUpdateDTO;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.author.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(AuthorController.class)  // 컨트롤러만 로드하여 테스트
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("createAuthor_Success")
    void createAuthor_Success() throws Exception {
        Author author = new Author();
        author.setName("test");

        given(authorService.addAuthor("test")).willReturn(author);

        mockMvc.perform(post("/task/author/{authorName}", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

    }


    @Test
    @DisplayName("modifyAuthor_Success")
    void modifyAuthor_Success() throws Exception {
        AuthorUpdateDTO dto = new AuthorUpdateDTO();
        dto.setAuthorId(1);
        dto.setAuthorName("newName");

        Author author = new Author();
        author.setAuthorId(1);
        author.setName("newName");

        given(authorService.updateAuthor(any(AuthorUpdateDTO.class))).willReturn(author);

        mockMvc.perform(put("/task/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"authorId\":1, \"authorName\":\"newName\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.name").value("newName"));

        verify(authorService).updateAuthor(any(AuthorUpdateDTO.class));
    }

    @Test
    @DisplayName("deleteAuthor_Success")
    void deleteAuthor_Success() throws Exception {
        int authorId = 1;

        mockMvc.perform(delete("/task/author/{authorId}", authorId))
                .andExpect(status().isNoContent());

        verify(authorService).deleteAuthor(authorId);
    }

    @Test
    @DisplayName("getAuthor_Success")
    void getAuthor_Success() throws Exception {
        int authorId = 1;
        Author author = new Author();
        author.setAuthorId(authorId);
        author.setName("testAuthor");

        given(authorService.getAuthor(authorId)).willReturn(author);

        mockMvc.perform(get("/task/author/{authorId}", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value(authorId))
                .andExpect(jsonPath("$.name").value("testAuthor"));

        verify(authorService).getAuthor(authorId);
    }

    @Test
    @DisplayName("getAuthorList_Success")
    void getAuthorList_Success() throws Exception {
        String name = "test";
        PageRequest pageable = PageRequest.of(0, 10);
        Author author = new Author();
        author.setAuthorId(1);
        author.setName("testAuthor");

        Page<Author> authors = new PageImpl<>(Collections.singletonList(author), pageable, 1);

        given(authorService.getAuthorList(pageable, name)).willReturn(authors);

        mockMvc.perform(get("/task/author/authorList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", name)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].authorId").value(1))
                .andExpect(jsonPath("$.content[0].name").value("testAuthor"));

        verify(authorService).getAuthorList(pageable, name);
    }


}