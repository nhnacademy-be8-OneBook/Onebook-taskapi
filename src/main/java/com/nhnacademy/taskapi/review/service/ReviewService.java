package com.nhnacademy.taskapi.review.service;

import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;
import org.springframework.data.domain.Page;

public interface ReviewService {
    ReviewResponse registerReview(ReviewRequest reviewRequest); // 리뷰 등록
    double getReviewGradeAverage(long bookId); // 리뷰 평점 평균
    Page<ReviewResponse> getReviewsByBook(long bookId, int page, int size);
    ReviewResponse updateReview(long reviewId, ReviewRequest reviewRequest);

    double getMemberAverageGrade(String memberId);

}
