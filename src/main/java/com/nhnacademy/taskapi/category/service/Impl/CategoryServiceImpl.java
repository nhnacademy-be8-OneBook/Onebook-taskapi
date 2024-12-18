package com.nhnacademy.taskapi.category.service.Impl;


import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.category.dto.CategoryUpdateDTO;
import com.nhnacademy.taskapi.category.exception.CategoryNameDuplicateException;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.exception.InvalidCategoryNameException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookCategoryService bookCategoryService;

    @Transactional
    @Override
    public Category addCategory(CategoryCreateDTO dto) {
        Category categories = new Category();

        if(Objects.nonNull(dto.getCategory())){
            categoryRepository.findById(dto.getCategory().getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found !"));
        }

        //카테고리 이름 중복 체크
        if(categoryRepository.existsByName(dto.getCategoryName())){
            throw new CategoryNameDuplicateException("Category name already exists ! categoryName: " + dto.getCategoryName());
        }


        categories.setName(dto.getCategoryName());
        categories.setParentCategory(dto.getCategory());

        return categoryRepository.save(categories);
    }

    @Transactional
    @Override
    public Category updateCategory(CategoryUpdateDTO updateDTO){
        Category category = categoryRepository.findById(updateDTO.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("this Category Not Exist !"));

        if(categoryRepository.existsByName(updateDTO.getCategoryName())){
            throw new CategoryNameDuplicateException("Category name already Exist !");
        }

        if(Objects.isNull(updateDTO.getCategoryName()) || updateDTO.getCategoryName().trim().isEmpty()){
            throw new InvalidCategoryNameException("This CategoryName is Null OR Empty");
        }
        category.setName(updateDTO.getCategoryName());


        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(int categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("this Category Not Exist !"));

        categoryRepository.delete(category);
    }
}
