package com.nhnacademy.taskapi.category.service.Impl;


import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.exception.CategoryNameDuplicateException;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(String categoryName, Category parentCategory) {
        Category categories = new Category();

        if(Objects.nonNull(parentCategory)){
            categoryRepository.findById(parentCategory.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found !"));
        }

        //카테고리 이름 중복 체크
        if(categoryRepository.existsByName(categoryName)){
            throw new CategoryNameDuplicateException("Category name already exists ! categoryName: " + categoryName);
        }


        categories.setName(categoryName);
        categories.setParentCategory(parentCategory);

        return categoryRepository.save(categories);
    }
}
