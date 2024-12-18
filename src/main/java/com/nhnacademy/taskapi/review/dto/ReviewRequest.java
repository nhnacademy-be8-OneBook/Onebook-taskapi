package com.nhnacademy.taskapi.review.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

// 리뷰 데이터를 전송하기 위함. 리뷰생성, 수정 요청시 사용
@Data
public class ReviewRequest {
    @NotNull
    private long memberId;

    @NotNull
    private long bookId;

    @Min(1)
    @Max(5)
    private int grade;

    @Column(nullable = false)
    private String description;

    @Size(max = 3)
    private List<String> imageUrl; // 최대 3장

    // 사진첨부
    public ReviewRequest(long memberId, long bookId, int grade, String description, List<String> imageUrl) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.grade = grade;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    // 사진 미첨부
    public ReviewRequest(long memberId, long bookId, int grade, String description) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.grade = grade;
        this.description = description;
    }
}
