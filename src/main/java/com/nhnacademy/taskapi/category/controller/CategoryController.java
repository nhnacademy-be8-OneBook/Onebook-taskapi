package com.nhnacademy.taskapi.category.controller;


import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.category.dto.CategoryUpdateDTO;
import com.nhnacademy.taskapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategories(@RequestBody CategoryCreateDTO dto){
        Category category = categoryService.addCategory(dto);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping
    public ResponseEntity<Category> modifyCategories(@RequestBody CategoryUpdateDTO dto){
        Category category = categoryService.updateCategory(dto);
        return ResponseEntity.ok().body(category);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategories(@PathVariable int categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
