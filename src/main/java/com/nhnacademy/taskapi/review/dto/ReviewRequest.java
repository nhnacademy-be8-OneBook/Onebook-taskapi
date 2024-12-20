package com.nhnacademy.taskapi.review.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

// 리뷰 데이터를 전송하기 위함. 리뷰생성, 수정 요청시 사용
@Data
public class ReviewRequest {

    @NotNull(message = "Member ID는 필수입니다.")
    private long memberId;

    @NotNull(message = "Book ID는 필수입니다.")
    private long bookId;

    @Min(value = 1, message = "평점은 1 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하여야 합니다.")
    private int grade;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    private String description;

    @Size(max = 3, message = "이미지는 최대 3장까지 첨부할 수 있습니다.")
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
