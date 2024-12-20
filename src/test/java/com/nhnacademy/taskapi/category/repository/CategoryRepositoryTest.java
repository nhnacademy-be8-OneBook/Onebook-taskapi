package com.nhnacademy.taskapi.category.repository;

import com.nhnacademy.taskapi.category.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void findByNameTest(){
        Category category = new Category();
        category.setName("test");
        category.setParentCategory(null);


        categoryRepository.save(category);

        Category result = categoryRepository.findByName("test");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("test");
    }

    @Test
    void existsByNameTest(){
        Category category = new Category();
        category.setName("test");
        category.setParentCategory(null);

        categoryRepository.save(category);

        boolean result = categoryRepository.existsByName("test");

        Assertions.assertEquals(true, result);

    }
}
