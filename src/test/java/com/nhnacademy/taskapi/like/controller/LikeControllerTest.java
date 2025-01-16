package com.nhnacademy.taskapi.like.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.like.service.LikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = LikeController.class)
public class LikeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;



    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /task/like - Add Like")
    void addLikeTest() throws Exception {
        // Given
        LikePlusMinusDTO requestDTO = new LikePlusMinusDTO();
        requestDTO.setBookId(1L);
        requestDTO.setMemberId(1L);

        Like like = new Like();
        like.setLikeId(1L);

        // When
        when(likeService.plusLike(any(LikePlusMinusDTO.class))).thenReturn(like);

        // Then
        mockMvc.perform(post("/task/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.likeId").value(1L));
    }

    @Test
    @DisplayName("DELETE /task/like - Delete Like")
    void deleteLikeTest() throws Exception {
        // Given
        LikePlusMinusDTO requestDTO = new LikePlusMinusDTO();
        requestDTO.setBookId(1L);
        requestDTO.setMemberId(1L);

        // When
        doNothing().when(likeService).minusLike(requestDTO);

        // Then
        mockMvc.perform(delete("/task/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNoContent());
    }

}
