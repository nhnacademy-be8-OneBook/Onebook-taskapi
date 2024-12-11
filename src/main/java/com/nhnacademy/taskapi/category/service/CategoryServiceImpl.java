package com.nhnacademy.taskapi.category.service;


import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public Category addCategory(int categoryId, String categoryName) {
        Category categories = new Category();
        categories.setParentCategory(categoryRepository.findById(categoryId).get());
        return categoryRepository.save(categories);
    }
}
