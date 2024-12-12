package com.nhnacademy.taskapi.review.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReviewResponse {
    private long reviewId;
    private int grade;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long memberId;
    private long bookId;
    private List<String> imageUrl;
}
