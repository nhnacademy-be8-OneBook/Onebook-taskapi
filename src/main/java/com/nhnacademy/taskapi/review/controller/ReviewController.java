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
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> registerReview(@Validated @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse response = reviewService.registerReview(reviewRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getReviewGradeAverage(@RequestParam long bookId) {
        double average = reviewService.getReviewGradeAverage(bookId);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByBook(
            @PathVariable long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReviewResponse> reviews = reviewService.getReviewsByBook(bookId, page, size);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable long reviewId,
            @Validated @RequestBody ReviewRequest reviewRequest) {
        ReviewResponse response = reviewService.updateReview(reviewId, reviewRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/member/{memberId}/grade")
    public ResponseEntity<Double> getMemberAverageGrade(@PathVariable String memberId) {
        double average = reviewService.getMemberAverageGrade(memberId);
        return ResponseEntity.ok(average);
    }


}
