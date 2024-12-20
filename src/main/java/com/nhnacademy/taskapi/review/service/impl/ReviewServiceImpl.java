package com.nhnacademy.taskapi.review.service.impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.service.MemberService;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.review.domain.Review;
import com.nhnacademy.taskapi.review.domain.ReviewImage;
import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;
import com.nhnacademy.taskapi.review.exception.ImageLimitExceededException;
import com.nhnacademy.taskapi.review.exception.InvalidReviewException;
import com.nhnacademy.taskapi.review.exception.ReviewAlreadyExistsException;
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
    private final MemberService memberService;
    private final BookService bookService;
    private final PointService pointService;

    @Override
    @Transactional
    public ReviewResponse registerReview(long bookId, ReviewRequest reviewRequest) {
        // 회원 존재 확인
        Member member = memberService.getMemberById(reviewRequest.getMemberId());

        // 도서 존재 확인
        Book book = bookService.getBook(bookId);

        // 동일 도서에 대한 리뷰 작성 여부 확인
        Optional<Review> existingReview = reviewRepository.findByMemberAndBook(member, book);
        if (existingReview.isPresent()) {
            throw new ReviewAlreadyExistsException("이미 해당 도서에 대한 리뷰를 작성하셨습니다.");
        }

        // 이미지 첨부 개수 확인
        if (reviewRequest.getImageUrl() != null && reviewRequest.getImageUrl().size() > 3) {
            throw new ImageLimitExceededException("이미지는 최대 3장까지 첨부할 수 있습니다.");
        }

        // 리뷰 생성
        Review review = new Review();
        review.setMember(member);
        review.setBook(book);
        review.setGrade(reviewRequest.getGrade());
        review.setDescription(reviewRequest.getDescription());
        review.setCreatedAt(LocalDateTime.now());

        // 사진 첨부를 체크
        boolean isPhotoAttached = false; // default false

        // 사진 추가
        if (reviewRequest.getImageUrl() != null && !reviewRequest.getImageUrl().isEmpty()) {
            for (String imageUrl : reviewRequest.getImageUrl()) {
                isPhotoAttached = true; // 사진 첨부 = true
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImageUrl(imageUrl);
                reviewImage.setReview(review);
                review.getReviewImage().add(reviewImage);
            }
        }

        // 리뷰 저장
        Review savedReview = reviewRepository.save(review);

        // 리뷰 작성시 사진 첨부 유무에 따라 포인트 적립
        // 근데 사진첨부해서 500포인트 받아놓고, 리뷰 수정해서 사진을 지우면...?
        pointService.registerReviewPoints(member, isPhotoAttached);

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
                .orElseThrow(() -> new InvalidReviewException("Invalid review ID"));

        // 리뷰 도서 일치 확인
        if (review.getBook().getBookId() != bookId) {
            throw new InvalidReviewException("리뷰가 해당 도서에 속하지 않습니다.");
        }

        // 리뷰 작성자 확인
        if (!review.getMember().getId().equals(reviewRequest.getMemberId())) {
            throw new InvalidReviewException("작성자만 리뷰를 수정할 수 있습니다.");
        }

        // 이미지 첨부 개수 확인
        if (reviewRequest.getImageUrl() != null && reviewRequest.getImageUrl().size() > 3) {
            throw new ImageLimitExceededException("이미지는 최대 3장까지 첨부할 수 있습니다.");
        }

        // 리뷰 정보 수정
        review.setGrade(reviewRequest.getGrade());
        review.setDescription(reviewRequest.getDescription());
        review.setUpdatedAt(LocalDateTime.now());

        // 이미지 업데이트
        review.getReviewImage().clear();
        if (reviewRequest.getImageUrl() != null && !reviewRequest.getImageUrl().isEmpty()) {
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
    @Transactional
    public ReviewResponse deleteReview(long bookId, long reviewId, ReviewRequest reviewRequest) {
        // 회원 존재 확인
        Member member = memberService.getMemberById(reviewRequest.getMemberId());

        // 리뷰 존재 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidReviewException("리뷰를 찾을 수 없습니다."));

        // 도서 일치 확인
        if (review.getBook().getBookId() != bookId) {
            throw new InvalidReviewException("리뷰가 해당 도서에 속하지 않습니다.");
        }

        // 관리자이거나 리뷰 작성자인지 확인
        boolean isAdmin = (member.getRole().getId() == 2);  // role_id = 2는 관리자

        if (!isAdmin) {
            throw new InvalidReviewException("해당 리뷰를 삭제할 권한이 없습니다.");
        }

        // 삭제 전 응답 DTO 생성
        // 삭제하기 전, 삭제될 리뷰의 모든 정보를 담아두고 이를 반환하기 위함..
        // client 가 어떤리뷰가 삭제되었는지 정보를 받을 수 있다..
        ReviewResponse response = ReviewResponse.builder()
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
                .build();

        // 리뷰 삭제
        reviewRepository.delete(review);

        return response;
    }

}

