package com.nhnacademy.taskapi.grade.controller;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.grade.service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GradeController {

    private final GradeService gradeService;

    // 전체 등급 조회
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> findAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok().body(grades);
    }

    // 인조키(id)로 등급 조회
//    @GetMapping("/grades/{id}")
//    public ResponseEntity<Grade> findOneGradeById(@PathVariable Long id) {
//        Grade grade = gradeService.getGradeById(id);
//        return ResponseEntity.ok().body(grade);
//    }

    // 이름(name)으로 등급 조회
    @GetMapping("/grades/{name}")
    public ResponseEntity<Grade> findOneGradeByName(@PathVariable String name) {
        Grade grade = gradeService.getGradeByName(name);
        return ResponseEntity.ok().body(grade);
    }

    // 등급 등록
    @PostMapping("/grades")
    public ResponseEntity<Grade> registerGrade(@RequestBody @Valid GradeRegisterDto gradeRegisterDto) {
        Grade grade = gradeService.registerGrade(gradeRegisterDto);
        return ResponseEntity.ok().body(grade);
    }

    // 등급 수정
    @PutMapping("/grades")
    public ResponseEntity<Grade> modifyGrade(@RequestBody @Valid GradeRegisterDto gradeRegisterDto) {
        Grade grade = gradeService.modifyGrade(gradeRegisterDto);
        return ResponseEntity.ok().body(grade);
    }

    // 등급 삭제 by name
    @DeleteMapping("/grades/{name}")
    public ResponseEntity<String> deleteGrade(@PathVariable String name) {
        gradeService.deleteGrade(name);
        return ResponseEntity.noContent().build();
    }

}
