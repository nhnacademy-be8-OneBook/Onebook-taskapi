package com.nhnacademy.taskapi.grade.service.impl;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeModifyDto;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.grade.exception.GradeAlreadyExistsException;
import com.nhnacademy.taskapi.grade.exception.GradeDataIntegrityViolationException;
import com.nhnacademy.taskapi.grade.exception.GradeIllegalArgumentException;
import com.nhnacademy.taskapi.grade.exception.GradeNotFoundException;
import com.nhnacademy.taskapi.grade.repository.GradeRepository;
import com.nhnacademy.taskapi.grade.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    // 모든 등급 조회
    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // default 등급(REGULAR) 가져오기 - default 등급(REGULAR)의 ID는 반드시 1.
    @Override
    public Grade getDefaultGrade() {
        return getGradeById(1);
    }

    // 인조키(id)로 등급 조회
    @Override
    public Grade getGradeById(Integer id) {
        if(!gradeRepository.existsById(id)) {
            throw new GradeIllegalArgumentException("Grade id does not exist");
        }

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(()->new GradeNotFoundException("Not Found grade by" + id));

        return grade;
    }

    // 중복 확인 - 회원 등급 이름(name)이 존재하는지 확인
    @Override
    public boolean existsByName(String name) {
        return gradeRepository.existsByName(name);
    }

    // 등급 등록
    @Override
    public Grade registerGrade(GradeRegisterDto gradeRegisterDto) {
        if(existsByName(gradeRegisterDto.name())) {
            throw new GradeAlreadyExistsException("Grade Name does Already exists");
        }

        Grade grade = Grade.create(
                gradeRegisterDto.name(),
                gradeRegisterDto.accumulationRate(),
                gradeRegisterDto.description()
        );

        try {

            return gradeRepository.save(grade);

        }catch(DataIntegrityViolationException e) {
            throw new GradeDataIntegrityViolationException("Failed to save grade in the database");
        }
    }

    // 등급 수정
    @Override
    public Grade modifyGrade(Integer id, GradeModifyDto gradeModifyDto) {
        Grade grade = getGradeById(id);
        grade.modifyGrade(gradeModifyDto.name(), gradeModifyDto.accumulationRate(), gradeModifyDto.description());

        try {
            return gradeRepository.save(grade);
        }catch(DataIntegrityViolationException e) {
            throw new GradeDataIntegrityViolationException("Failed to save grade in the database");
        }
    }

    // 회원 등급 삭제
    @Override
    public void removeGrade(Integer id) {
        Grade grade = getGradeById(id);

        gradeRepository.delete(grade);
    }

}
