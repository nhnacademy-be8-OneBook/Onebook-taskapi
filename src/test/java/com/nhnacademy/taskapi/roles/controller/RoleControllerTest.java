package com.nhnacademy.taskapi.roles.controller;

import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterDto;
import com.nhnacademy.taskapi.roles.service.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;


    @Test
    @DisplayName("GET All Roles")
    void getAllTest() throws Exception {
        List<Role> roleList = Arrays.asList(Role.createRole("MEMBER", "일반 회원"));

        Mockito.when(roleService.getAllRoles()).thenReturn(roleList);

        mockMvc.perform(get("/task/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("MEMBER"))
                .andExpect(jsonPath("$[0].description").value("일반 회원"));
    }

    @Test
    @DisplayName("GET One Role")
    void getOneTest() throws Exception {
        Role role = Role.createRole("MEMBER", "일반 회원");

        Mockito.when(roleService.getRoleById(1)).thenReturn(role);

        mockMvc.perform(get("/task/roles/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MEMBER"))
                .andExpect(jsonPath("$.description").value("일반 회원"));
    }

    @Test
    @DisplayName("POST Role")
    void createTest() throws Exception {
        Role role = Role.createRole("MEMBER", "일반 회원");

        Mockito.when(roleService.registerRole(new RoleRegisterDto("MEMBER", "일반 회원"))).thenReturn(role);

        mockMvc.perform(post("/task/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"MEMBER\",\"description\":\"일반 회원\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MEMBER"))
                .andExpect(jsonPath("$.description").value("일반 회원"));
    }

    @Test
    @DisplayName("PUT Role")
    void updateTest() throws Exception {
        Role role = Role.createRole("ADMIN", "관리자");

        Mockito.when(roleService.modifyRole(1, new RoleModifyDto("ADMIN", "관리자"))).thenReturn(role);

        mockMvc.perform(put("/task/roles/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"ADMIN\", \"description\": \"관리자\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andExpect(jsonPath("$.description").value("관리자"));
    }

    @Test
    @DisplayName("DELETE Role")
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/task/roles/{id}",1))
                .andExpect(status().is2xxSuccessful());
    }

}
