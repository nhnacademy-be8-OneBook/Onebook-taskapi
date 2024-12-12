package com.nhnacademy.taskapi.review.service.impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.review.domain.Review;
import com.nhnacademy.taskapi.review.domain.ReviewImage;
import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;
import com.nhnacademy.taskapi.review.repository.ReviewRepository;
import com.nhnacademy.taskapi.review.repository.ReviewImageRepository;
import com.nhnacademy.taskapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ReviewResponse registerReview(ReviewRequest reviewRequest) {
        // 회원 존재 확인
        Member member = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // 도서 존재 확인
        Book book = bookRepository.findById(reviewRequest.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // 구매 여부 확인
        // 필요시 구현, 우선은 마이페이지에서 리뷰 작성 = 이미 구매가 됐다고 인증된 사용자니까..

        // 리뷰 생성
        Review review = new Review();
        review.setMember(member);
        review.setBook(book);
        review.setGrade(reviewRequest.getGrade());
        review.setDescription(reviewRequest.getDescription());
        review.setCreatedAt(LocalDateTime.now());

        // 리뷰 저장
        Review savedReview = reviewRepository.save(review);

        // 이미지 저장 (최대 3장)
        if (reviewRequest.getImageUrl() != null) {
            if (reviewRequest.getImageUrl().size() > 3) {
                throw new IllegalArgumentException("이미지는 최대 3장까지 첨부할 수 있습니다.");
            }

            for (String imageUrl : reviewRequest.getImageUrl()) {
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImageUrl(imageUrl);
                reviewImage.setReview(savedReview);
                reviewImageRepository.save(reviewImage);
            }
        }

        // 응답 DTO 생성
        return ReviewResponse.builder()
                .reviewId(savedReview.getReviewId())
                .grade(savedReview.getGrade())
                .description(savedReview.getDescription())
                .createdAt(savedReview.getCreatedAt())
                .updatedAt(savedReview.getUpdatedAt())
                .memberId(member.getId())
                .bookId(book.getBookId())
                .imageUrl(
                        savedReview.getReviewImage().stream()
                                .map(ReviewImage::getImageUrl)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public double getReviewGradeAverage(long bookId) {
        return reviewRepository.findAverageGradeByBookId(bookId)
                .orElse(0.0);
    }
}
