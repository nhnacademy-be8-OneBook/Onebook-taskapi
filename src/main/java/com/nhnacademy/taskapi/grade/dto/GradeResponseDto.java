package com.nhnacademy.taskapi.grade.dto;

import com.nhnacademy.taskapi.grade.domain.Grade;

public record GradeResponseDto(

        /**
         * 1: REGULAR, 2: ROYAL, 3: GOLD, 4: PLATINUM
         */

        Integer id,
        String name,
        int accumulationRate,
        String description
)
{
     public static GradeResponseDto from(Grade grade) {
         return new GradeResponseDto(
                 grade.getId(), grade.getName(), grade.getAccumulationRate(), grade.getDescription()
         );
     }
}
