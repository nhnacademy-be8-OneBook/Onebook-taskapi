package com.nhnacademy.taskapi.category.service;

import com.nhnacademy.taskapi.category.domain.Category;

public interface CategoryService {
    Category addCategory(int categoryId, String categoryName);
}
