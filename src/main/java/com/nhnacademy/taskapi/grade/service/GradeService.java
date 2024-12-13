package com.nhnacademy.taskapi.grade.service;

import com.nhnacademy.taskapi.grade.domain.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> getAllGrades();
    Grade getDefaultGrade();
    Grade getGradeById(Long id);
    Grade getGradeByName(String name);
}
