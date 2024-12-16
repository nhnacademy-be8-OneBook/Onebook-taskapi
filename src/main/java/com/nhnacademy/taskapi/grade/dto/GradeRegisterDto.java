package com.nhnacademy.taskapi.grade.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record GradeRegisterDto(
        @NotBlank(message="등급 이름은 필수 입력 사항입니다.") String name,
        @Min(value=0, message = "적립률은 0 이상이어야 합니다.") int accumulationRate,
        @NotBlank(message = "등급 설명은 필수 입력 사항입니다.") String description
) {}
