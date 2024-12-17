package com.nhnacademy.taskapi.grade.repository;

import com.nhnacademy.taskapi.grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    boolean existsByName(String name);
}
