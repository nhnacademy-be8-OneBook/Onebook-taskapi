package com.nhnacademy.taskapi.review.controller;

import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;
import com.nhnacademy.taskapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/books/{bookId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping
    public ResponseEntity<ReviewResponse> registerReview(
            @PathVariable long bookId,
            @Validated @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse response = reviewService.registerReview(bookId, reviewRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 도서의 리뷰 평점 평균
    @GetMapping("/average")
    public ResponseEntity<Double> getReviewGradeAverage(@PathVariable long bookId) {
        double average = reviewService.getReviewGradeAverage(bookId);
        return ResponseEntity.ok(average);
    }

    // 도서의 리뷰 목록 조회, 페이징 처리
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getReviewsByBook(
            @PathVariable long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReviewResponse> reviews = reviewService.getReviewByBook(bookId, page, size);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable long bookId,
            @PathVariable long reviewId,
            @Validated @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse response = reviewService.updateReview(bookId, reviewId, reviewRequest);
        return ResponseEntity.ok(response);
    }
}
