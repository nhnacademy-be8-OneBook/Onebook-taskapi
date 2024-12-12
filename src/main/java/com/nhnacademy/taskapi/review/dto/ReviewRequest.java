// ReviewRequest.java
package com.nhnacademy.taskapi.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ReviewRequest {
    @NotNull
    private long memberId;

    @NotNull
    private long bookId;

    @Min(1)
    @Max(5)
    private int grade;

    @NotBlank
    private String description;

    @Max(3)
    private List<String> imageUrl; // 최대 3장
}
