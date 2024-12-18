package com.nhnacademy.taskapi.category.dto;


import com.nhnacademy.taskapi.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {
    private String categoryName;
    private Category category;
}
