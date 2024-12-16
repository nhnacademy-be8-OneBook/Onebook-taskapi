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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
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
    public ReviewResponse registerReview(long bookId, ReviewRequest reviewRequest) {
        // 회원 존재 확인
        Member member = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // 도서 존재 확인
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // 동일 도서에 대한 리뷰 작성 여부 확인
        Optional<Review> existingReview = reviewRepository.findByMemberAndBook(member, book);
        if (existingReview.isPresent()) {
            throw new IllegalArgumentException("이미 해당 도서에 대한 리뷰를 작성하셨습니다.");
        }

        // 리뷰 생성
        Review review = new Review();
        review.setMember(member);
        review.setBook(book);
        review.setGrade(reviewRequest.getGrade());
        review.setDescription(reviewRequest.getDescription());
        review.setCreatedAt(LocalDateTime.now());

        // 이미지 추가
        if (reviewRequest.getImageUrl() != null && !reviewRequest.getImageUrl().isEmpty()) {
            if (reviewRequest.getImageUrl().size() > 3) {
                throw new IllegalArgumentException("이미지는 최대 3장까지 첨부할 수 있습니다.");
            }

            for (String imageUrl : reviewRequest.getImageUrl()) {
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImageUrl(imageUrl);
                reviewImage.setReview(review);
                review.getReviewImage().add(reviewImage);
            }
        }

        // 리뷰 저장
        Review savedReview = reviewRepository.save(review);

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

    @Override
    public Page<ReviewResponse> getReviewByBook(long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviewPage = reviewRepository.findByBookBookId(bookId, pageable);

        return reviewPage.map(review -> ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .grade(review.getGrade())
                .description(review.getDescription())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .memberId(review.getMember().getId())
                .bookId(review.getBook().getBookId())
                .imageUrl(review.getReviewImage().stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .build());
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(long bookId, long reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        // 리뷰 도서 일치 확인
        if (review.getBook().getBookId() != bookId) {
            throw new IllegalArgumentException("리뷰가 해당 도서에 속하지 않습니다.");
        }

        // 리뷰 작성자 확인
        if (!review.getMember().getId().equals(reviewRequest.getMemberId())) {
            throw new IllegalArgumentException("작성자만 리뷰를 수정할 수 있습니다.");
        }

        review.setGrade(reviewRequest.getGrade());
        review.setDescription(reviewRequest.getDescription());
        review.setUpdatedAt(LocalDateTime.now());

        // 이미지 업데이트
        review.getReviewImage().clear();
        if (reviewRequest.getImageUrl() != null && !reviewRequest.getImageUrl().isEmpty()) {
            if (reviewRequest.getImageUrl().size() > 3) {
                throw new IllegalArgumentException("이미지는 최대 3장까지 첨부할 수 있습니다.");
            }

            for (String imageUrl : reviewRequest.getImageUrl()) {
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImageUrl(imageUrl);
                reviewImage.setReview(review);
                review.getReviewImage().add(reviewImage);
            }
        }

        // 리뷰 저장
        Review updatedReview = reviewRepository.save(review);

        // 응답 DTO 생성
        return ReviewResponse.builder()
                .reviewId(updatedReview.getReviewId())
                .grade(updatedReview.getGrade())
                .description(updatedReview.getDescription())
                .createdAt(updatedReview.getCreatedAt())
                .updatedAt(updatedReview.getUpdatedAt())
                .memberId(updatedReview.getMember().getId())
                .bookId(updatedReview.getBook().getBookId())
                .imageUrl(updatedReview.getReviewImage().stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public double getMemberAverageGrade(String memberId) {
        return reviewRepository.findAverageGradeByMemberId(memberId)
                .orElse(0.0);
    }
}
