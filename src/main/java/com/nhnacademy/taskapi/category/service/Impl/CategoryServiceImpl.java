package com.nhnacademy.taskapi.category.service.Impl;


import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.exception.CategoryNameDuplicateException;
import com.nhnacademy.taskapi.category.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookCategoryService bookCategoryService;

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

    @Override
    public Category updateCategory(int categoryId, String categoryName){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("this Category Not Exist !"));

        if(categoryRepository.existsByName(categoryName)){
            throw new CategoryNameDuplicateException("Category name already Exist !");
        }
        category.setName(categoryName);

        List<BookCategory> bookCategories = bookCategoryService.getBookByCategory(category);
        for(BookCategory bk : bookCategories){
            BookCategorySaveDTO dto = new BookCategorySaveDTO();

            dto.setCategoryId(bk.getCategory().getCategoryId());
            dto.setBookId(bk.getBook().getBookId());

            bk.setCategory(category);

            bookCategoryService.save(dto);

        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("this Category Not Exist !"));

        categoryRepository.delete(category);
    }
}
