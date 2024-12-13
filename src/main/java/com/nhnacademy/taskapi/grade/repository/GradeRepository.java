package com.nhnacademy.taskapi.grade.repository;

import com.nhnacademy.taskapi.grade.domain.Grades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grades, Long> {
    Optional<Grades> findByName(String name);
}
