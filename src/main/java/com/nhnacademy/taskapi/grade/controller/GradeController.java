package com.nhnacademy.taskapi.grade.controller;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeModifyDto;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.grade.dto.GradeResponseDto;
import com.nhnacademy.taskapi.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/grades")
public class GradeController {

    private final GradeService gradeService;

    // 전체 등급 조회
    @GetMapping
    public ResponseEntity<List<GradeResponseDto>> getGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        List<GradeResponseDto> gradeResponseDtoList = new ArrayList<>();
        for (Grade grade : grades) {
            gradeResponseDtoList.add(GradeResponseDto.from(grade));
        }
        return ResponseEntity.ok().body(gradeResponseDtoList);
    }

    // 인조키(id)로 단일 등급 조회
    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Integer id) {
        Grade grade = gradeService.getGradeById(id);
        GradeResponseDto gradeResponseDto = GradeResponseDto.from(grade);

        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 등록
    @PostMapping
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody @Valid GradeRegisterDto gradeRegisterDto) {
        Grade grade = gradeService.registerGrade(gradeRegisterDto);
        GradeResponseDto gradeResponseDto = GradeResponseDto.from(grade);

        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 수정
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Integer id, @RequestBody @Valid GradeModifyDto gradeModifyDto) {
        Grade grade = gradeService.modifyGrade(id, gradeModifyDto);
        GradeResponseDto gradeResponseDto = GradeResponseDto.from(grade);

        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Integer id) {
        gradeService.removeGrade(id);
        return ResponseEntity.noContent().build();
    }

}
