package com.nhnacademy.taskapi.serviceImplTest;


import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.category.exception.CategoryNameDuplicateException;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.category.service.Impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    void addCategory_Successful() {
        // Given
        String categoryName = "Books";
        Category parentCategory = new Category();
        parentCategory.setCategoryId(1);

        // 부모 카테고리 존재 확인 및 이름 중복 확인
        when(categoryRepository.findById(1)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.existsByName(categoryName)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        CategoryCreateDTO dto = new CategoryCreateDTO();
        dto.setCategory(parentCategory);
        dto.setCategoryName(categoryName);

        Category result = categoryService.addCategory(dto);

        // Then
        assertNotNull(result);
        assertEquals(categoryName, result.getName());
        assertEquals(parentCategory, result.getParentCategory());
        verify(categoryRepository).findById(1);
        verify(categoryRepository).existsByName(categoryName);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void addCategory_ThrowsCategoryNotFoundException() {
        // Given
        String categoryName = "Books";
        Category parentCategory = new Category();
        parentCategory.setCategoryId(1);

        // 부모 카테고리가 존재하지 않는 경우
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        CategoryCreateDTO dto = new CategoryCreateDTO();
        dto.setCategory(parentCategory);
        dto.setCategoryName(categoryName);

        // When & Then
        assertThrows(CategoryNotFoundException.class, () -> categoryService.addCategory(dto));
        verify(categoryRepository).findById(1);
        verify(categoryRepository, never()).existsByName(anyString());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void addCategory_ThrowsCategoryNameDuplicateException() {
        // Given
        String categoryName = "Books";

        // 카테고리 이름이 중복된 경우
        when(categoryRepository.existsByName(categoryName)).thenReturn(true);

        CategoryCreateDTO dto = new CategoryCreateDTO();
        dto.setCategory(null);
        dto.setCategoryName(categoryName);

        // When & Then
        assertThrows(CategoryNameDuplicateException.class, () -> categoryService.addCategory(dto));
        verify(categoryRepository, never()).findById(anyInt());
        verify(categoryRepository).existsByName(categoryName);
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
