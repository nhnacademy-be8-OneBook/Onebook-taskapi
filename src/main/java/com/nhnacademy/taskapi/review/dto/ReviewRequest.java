package com.nhnacademy.taskapi.review.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ReviewRequest {
    @NotNull
    private String memberId;

    @NotNull
    private long bookId;

    @Min(1)
    @Max(5)
    private int grade;

    @NotBlank
    private String description;

    @Size(max = 3)
    private List<String> imageUrl; // 최대 3장

    public ReviewRequest(String memberId, long bookId, int grade, String description, List<String> imageUrl) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.grade = grade;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public ReviewRequest(String memberId, long bookId, int grade, String description) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.grade = grade;
        this.description = description;
    }
}
