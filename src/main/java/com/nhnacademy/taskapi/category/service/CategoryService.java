package com.nhnacademy.taskapi.category.service;

import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.category.dto.CategoryUpdateDTO;

public interface CategoryService {
    Category addCategory(CategoryCreateDTO dto);
    Category updateCategory(CategoryUpdateDTO updateDTO);
    void deleteCategory(int categoryId);

    Category addCategoryByAladin(String categoryName);
    Category getCategory(int categoryId);
}
