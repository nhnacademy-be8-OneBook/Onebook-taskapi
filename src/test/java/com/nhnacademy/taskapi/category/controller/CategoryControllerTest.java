package com.nhnacademy.taskapi.category.controller;


import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.category.dto.CategoryUpdateDTO;
import com.nhnacademy.taskapi.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;


    @Test
    public void testCreateCategoryWithParentAndChild() throws Exception {
        // 부모 카테고리 객체 설정
        Category parentCategory = new Category();
        parentCategory.setCategoryId(1);
        parentCategory.setName("Fiction");

        // 자식 카테고리 객체 설정
        Category childCategory = new Category();
        childCategory.setCategoryId(2);
        childCategory.setName("Science Fiction");
        childCategory.setParentCategory(parentCategory);  // 부모 카테고리 설정

        // CategoryService 모킹: 자식 카테고리 생성
        when(categoryService.addCategory(Mockito.any())).thenReturn(childCategory);

        // POST 요청 테스트 (자식 카테고리 생성)
        mockMvc.perform(post("/task/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Science Fiction\", \"parentCategoryId\": 1}"))
                .andExpect(status().isOk()) // 상태 코드 200 OK
                .andExpect(content().json("{\"categoryId\": 2, \"name\": \"Science Fiction\", \"parentCategory\": {\"categoryId\": 1, \"name\": \"Fiction\"}}")); // 부모 카테고리 확인
    }

    @Test
    public void testModifyCategory() throws Exception {
        // 테스트에 사용할 카테고리 객체 설정
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Updated Fiction");

        // CategoryService 모킹: 카테고리 수정
        when(categoryService.updateCategory(Mockito.any())).thenReturn(category);

        // PUT 요청 테스트
        mockMvc.perform(put("/task/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"categoryId\": 1, \"name\": \"Updated Fiction\"}"))
                .andExpect(status().isOk()) // 상태 코드 200 OK
                .andExpect(content().json("{\"categoryId\": 1, \"name\": \"Updated Fiction\"}")); // 수정된 카테고리 확인
    }

    @Test
    public void testDeleteCategory() throws Exception {
        // CategoryService 모킹: 카테고리 삭제
        doNothing().when(categoryService).deleteCategory(Mockito.anyInt());

        // DELETE 요청 테스트
        mockMvc.perform(delete("/task/categories/{categoryId}", 1))
                .andExpect(status().isNoContent()); // 상태 코드 204 No Content
    }

    @Test
    public void testGetCategory() throws Exception {
        // 테스트에 사용할 카테고리 객체 설정
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Fiction");

        // CategoryService 모킹: 카테고리 조회
        when(categoryService.getCategory(Mockito.anyInt())).thenReturn(category);

        // GET 요청 테스트
        mockMvc.perform(get("/task/categories/{categoryId}", 1))
                .andExpect(status().isOk()) // 상태 코드 200 OK
                .andExpect(content().json("{\"categoryId\": 1, \"name\": \"Fiction\"}")); // 카테고리 정보 확인
    }
}
