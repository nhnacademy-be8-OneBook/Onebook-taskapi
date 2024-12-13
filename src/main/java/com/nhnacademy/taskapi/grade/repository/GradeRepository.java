package com.nhnacademy.taskapi.grade.repository;

import com.nhnacademy.taskapi.grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByName(String name);
}
