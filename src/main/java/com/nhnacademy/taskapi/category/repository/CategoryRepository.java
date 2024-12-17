package com.nhnacademy.taskapi.category.repository;

import com.nhnacademy.taskapi.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    boolean existsByName(String name);
}
