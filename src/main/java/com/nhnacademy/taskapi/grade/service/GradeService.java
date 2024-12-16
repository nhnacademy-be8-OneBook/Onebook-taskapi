package com.nhnacademy.taskapi.grade.service;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;

import java.util.List;

public interface GradeService {
    List<Grade> getAllGrades();
    Grade getDefaultGrade();
    Grade getGradeById(Integer id);
    Grade getGradeByName(String name);
    Grade registerGrade(GradeRegisterDto gradeRegisterDto);
    boolean existsByName(String name);
    Grade modifyGrade(GradeRegisterDto gradeRegisterDto);
    void deleteGrade(String name);
}
