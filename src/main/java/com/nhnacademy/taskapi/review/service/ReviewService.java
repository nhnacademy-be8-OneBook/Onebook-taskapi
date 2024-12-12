package com.nhnacademy.taskapi.review.service;

import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;

public interface ReviewService {
    ReviewResponse registerReview(ReviewRequest reviewRequest); // 리뷰 등록
    double getReviewGradeAverage(long bookId); // 리뷰 평점 평균
}
