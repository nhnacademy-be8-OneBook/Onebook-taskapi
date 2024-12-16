package com.nhnacademy.taskapi.grade.service.impl;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.grade.exception.GradeAlreadyExistsException;
import com.nhnacademy.taskapi.grade.exception.GradeIllegalArgumentException;
import com.nhnacademy.taskapi.grade.exception.GradeNotFoundException;
import com.nhnacademy.taskapi.grade.repository.GradeRepository;
import com.nhnacademy.taskapi.grade.service.GradeService;
import lombok.RequiredArgsConstructor;
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

    // default 등급(REGULAR) 가져오기
    @Override
    public Grade getDefaultGrade() {
        return getGradeByName("REGULAR");
    }

    // 인조키(id)로 등급 조회
    @Override
    public Grade getGradeById(Integer id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(()->new GradeNotFoundException("Not Found grade by" + id));

        return grade;
    }

    // 이름(name)으로 등급 조회
    @Override
    public Grade getGradeByName(String name) {
        Grade grade = gradeRepository.findByName(name)
                .orElseThrow(() -> new GradeNotFoundException("Not Found Grade by" + name));

        return grade;
    }

    // 중복 확인 - 회원 등급 이름(name)이 존재하는지 확인
    @Override
    public boolean existsByName(String name) {
        return gradeRepository.existsByName(name);
    }

    // 회원 등급 등록
    @Override
    public Grade registerGrade(GradeRegisterDto gradeRegisterDto) {
        if(existsByName(gradeRegisterDto.name())) {
            throw new GradeAlreadyExistsException("Grade Name does Already exists");
        }

        Grade grade = new Grade(
                gradeRegisterDto.name(),
                gradeRegisterDto.accumulationRate(),
                gradeRegisterDto.description()
        );

        return gradeRepository.save(grade);
    }


    // 회원 등급 수정 - name은 변경 불가능.
    @Override
    public Grade modifyGrade(GradeRegisterDto gradeRegisterDto) {
        Grade grade = getGradeByName(gradeRegisterDto.name());

        grade.updateGrade(gradeRegisterDto.accumulationRate(), gradeRegisterDto.description());

        return gradeRepository.save(grade);
    }

    // 회원 등급 삭제 by Name
    @Override
    public void deleteGrade(String name) {
        Grade grade = getGradeByName(name);

        gradeRepository.delete(grade);
    }

}
