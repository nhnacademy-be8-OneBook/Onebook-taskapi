package com.nhnacademy.taskapi.grade.service;

import com.nhnacademy.taskapi.grade.domain.Grades;

import java.util.List;

public interface GradeService {
    List<Grades> getAllGrades();
    Grades getDefaultGrade();
    Grades getGradeById(Long id);
    Grades getGradeByName(String name);
}
