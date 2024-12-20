package com.nhnacademy.taskapi.grade.controller;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeModifyDto;
import com.nhnacademy.taskapi.grade.service.GradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = GradeController.class)
public class GradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GradeService gradeService;

    @Test
    @DisplayName("GET All Grades")
    void getAllTest() throws Exception {
        List<Grade> gradeList = Arrays.asList(Grade.create("REGULAR", 1, "test"));

        Mockito.when(gradeService.getAllGrades()).thenReturn(gradeList);

        mockMvc.perform(get("/task/grades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("REGULAR"))
                .andExpect(jsonPath("$[0].accumulationRate").value(1))
                .andExpect(jsonPath("$[0].description").value("test"));
    }

    @Test
    @DisplayName("GET One Grade")
    void getOneTest() throws Exception {
        Grade grade = Grade.create("REGULAR", 1, "test");

        Mockito.when(gradeService.getGradeById(Mockito.anyInt())).thenReturn(grade);

        mockMvc.perform(get("/task/grades/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("REGULAR"))
                .andExpect(jsonPath("$.accumulationRate").value(1))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    @DisplayName("POST Grade")
    void postTest() throws Exception {
        Grade grade = Grade.create("REGULAR", 1, "test");

        Mockito.when(gradeService.registerGrade(Mockito.any())).thenReturn(grade);

        mockMvc.perform(post("/task/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"REGULAR\", \"accumulationRate\": 1, \"description\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("REGULAR"))
                .andExpect(jsonPath("$.accumulationRate").value(1))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    @DisplayName("PUT Grade")
    void putTest() throws Exception {
        Grade grade = Grade.create("TEST", 2, "TEST");

        Mockito.when(gradeService.modifyGrade(1, new GradeModifyDto("TEST", 2, "TEST"))).thenReturn(grade);

        mockMvc.perform(put("/task/grades/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"TEST\", \"accumulationRate\": 2, \"description\": \"TEST\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TEST"))
                .andExpect(jsonPath("$.accumulationRate").value(2))
                .andExpect(jsonPath("$.description").value("TEST"));
    }

    @Test
    @DisplayName("DELETE Grade")
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/task/grades/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}
