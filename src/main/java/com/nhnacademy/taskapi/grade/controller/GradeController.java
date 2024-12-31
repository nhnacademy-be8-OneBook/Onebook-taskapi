package com.nhnacademy.taskapi.grade.controller;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeModifyRequestDto;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterRequestDto;
import com.nhnacademy.taskapi.grade.dto.GradeResponseDto;
import com.nhnacademy.taskapi.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/grades")
public class GradeController {

    private final GradeService gradeService;

    // 전체 등급 조회
    @GetMapping("/list")
    public ResponseEntity<List<GradeResponseDto>> getGrades() {
        List<GradeResponseDto> gradeResponseDtoList = gradeService.getAllGrades();
        return ResponseEntity.ok().body(gradeResponseDtoList);
    }

    // 인조키(id)로 단일 등급 조회 - url에 id 명시. for 관리자.
    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Integer id) {
        GradeResponseDto gradeResponseDto = gradeService.getGradeById(id);
        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 등록
    @PostMapping
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody @Valid GradeRegisterRequestDto gradeRegisterRequestDto) {
        GradeResponseDto gradeResponseDto = gradeService.registerGrade(gradeRegisterRequestDto);
        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 수정
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Integer id, @RequestBody @Valid GradeModifyRequestDto gradeModifyRequestDto) {
        GradeResponseDto gradeResponseDto = gradeService.modifyGrade(id, gradeModifyRequestDto);
        return ResponseEntity.ok().body(gradeResponseDto);
    }

    // 등급 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Integer id) {
        gradeService.removeGrade(id);
        return ResponseEntity.noContent().build();
    }

}
