package com.nhnacademy.taskapi.grade.service.impl;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.exception.GradeNotFoundException;
import com.nhnacademy.taskapi.grade.repository.GradeRepository;
import com.nhnacademy.taskapi.grade.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    // 모든 등급 가져오기
    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // default 등급 (INACTIVE) 가져오기
    @Override
    public Grade getDefaultGrade() {
        Grade grade = gradeRepository.findByName("INACTIVE").orElse(null);
        if (Objects.isNull(grade)) {
            throw new GradeNotFoundException("Not Found Grade INACTIVE");
        }
        return grade;
    }

    // 인조키(id)로 등급 가져오기
    @Override
    public Grade getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id).orElse(null);
        if (Objects.isNull(grade)) {
            throw new GradeNotFoundException("Not Found Grade " + id);
        }
        return grade;
    }

    // 이름(name)으로 등급 가져오기
    @Override
    public Grade getGradeByName(String name) {
        Grade grade = gradeRepository.findByName(name).orElse(null);
        if (Objects.isNull(grade)) {
            throw new GradeNotFoundException("Not Found Grade " + name);
        }
        return grade;
    }


}
