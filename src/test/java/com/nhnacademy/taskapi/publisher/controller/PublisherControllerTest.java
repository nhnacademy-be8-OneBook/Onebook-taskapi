package com.nhnacademy.taskapi.publisher.controller;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.dto.PublisherUpdateDTO;
import com.nhnacademy.taskapi.publisher.service.PublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Test
    @DisplayName("출판사 생성 테스트")
    void testCreatePublisher() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("TestPublisher");

        when(publisherService.addPublisher("TestPublisher")).thenReturn(publisher);

        mockMvc.perform(post("/task/publisher/TestPublisher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestPublisher"));
    }

    @Test
    @DisplayName("출판사 수정 테스트")
    void testModifyPublisher() throws Exception {
        PublisherUpdateDTO updateDTO = new PublisherUpdateDTO();
        updateDTO.setPublisherId(1L);
        updateDTO.setPublisherName("UpdatedPublisher");

        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setName("UpdatedPublisher");

        when(publisherService.updatePublisher(any(PublisherUpdateDTO.class))).thenReturn(updatedPublisher);

        mockMvc.perform(put("/task/publisher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"UpdatedPublisher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedPublisher"));
    }

    @Test
    @DisplayName("출판사 삭제 테스트")
    void testDeletePublisher() throws Exception {
        Mockito.doNothing().when(publisherService).deletePublisher(1L);

        mockMvc.perform(delete("/task/publisher/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("출판사 조회 테스트")
    void testGetPublisher() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("TestPublisher");

        when(publisherService.getPublisher("TestPublisher")).thenReturn(publisher);

        mockMvc.perform(get("/task/publisher/TestPublisher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestPublisher"));
    }

    @Test
    @DisplayName("출판사 목록 조회 테스트")
    void testGetPublisherList() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("TestPublisher");
        List<Publisher> publishers = Collections.singletonList(publisher);

        when(publisherService.getPublisherList("TestPublisher")).thenReturn(publishers);

        mockMvc.perform(get("/task/publisher/list/TestPublisher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestPublisher"));
    }

}
