package com.nhnacademy.taskapi.review.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.domain.Member.Gender;
import com.nhnacademy.taskapi.member.domain.Member.Status;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.service.MemberService;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.review.domain.Review;
import com.nhnacademy.taskapi.review.dto.ReviewRequest;
import com.nhnacademy.taskapi.review.dto.ReviewResponse;
import com.nhnacademy.taskapi.review.exception.ImageLimitExceededException;
import com.nhnacademy.taskapi.review.exception.InvalidReviewException;
import com.nhnacademy.taskapi.review.exception.ReviewAlreadyExistsException;
import com.nhnacademy.taskapi.review.repository.ReviewImageRepository;
import com.nhnacademy.taskapi.review.repository.ReviewRepository;
import com.nhnacademy.taskapi.review.service.impl.ReviewServiceImpl;
import com.nhnacademy.taskapi.roles.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewImageRepository reviewImageRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private BookService bookService;

    @Mock
    private PointService pointService; // 포인트 서비스 Mock 추가

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Member member;       // 일반 사용자
    private Member adminMember;  // 관리자
    private Book book;
    private Review review;
    private Role memberRole;
    private Role adminRole;
    private Grade grade;

    @BeforeEach
    void setUp() throws Exception {
        // Grade 객체 생성
        grade = Grade.create("Gold", 10, "WoW, You Are BlackCow");

        // MEMBER Role 생성
        memberRole = Role.createRole("MEMBER", "내 돈줄");
        Field memberRoleIdField = Role.class.getDeclaredField("id");
        memberRoleIdField.setAccessible(true);
        memberRoleIdField.set(memberRole, 1);

        // ADMIN Role 생성
        adminRole = Role.createRole("ADMIN", "Administrator role");
        Field adminRoleIdField = Role.class.getDeclaredField("id");
        adminRoleIdField.setAccessible(true);
        adminRoleIdField.set(adminRole, 2);

        // Member(일반 사용자) 객체 생성
        member = Member.createNewMember(
                grade,
                "집가고싶다",
                "wantgohome",
                "showmethemoney",
                LocalDate.of(1990, 1, 1),
                Gender.M,
                "ebul@outside.dangerous",
                "123-456-7890",
                memberRole
        );
        Field memberIdField = Member.class.getDeclaredField("id");
        memberIdField.setAccessible(true);
        memberIdField.set(member, 1L);
        member.setStatus(Status.ACTIVE);

        // Admin Member(관리자) 생성
        adminMember = Member.createNewMember(
                grade,
                "관리자",
                "adminuser",
                "adminpass",
                LocalDate.of(2024, 12, 19),
                Gender.M,
                "admin@test.com",
                "999-999-9999",
                adminRole
        );
        Field adminIdField = Member.class.getDeclaredField("id");
        adminIdField.setAccessible(true);
        adminIdField.set(adminMember, 2L);
        adminMember.setStatus(Status.ACTIVE);

        // Book 객체 생성
        book = new Book();
        book.setBookId(1L);
        book.setTitle("빠르게 퇴근 하는 방법");
        book.setDescription("돈 많은 백수가 되어라");
        book.setIsbn13("1234567890123");
        book.setPrice(20000);
        book.setSalePrice(18000);
        book.setAmount(100);
        book.setViews(0);
        book.setPubdate(LocalDate.now());

        // Review 객체 생성 및 초기화
        review = new Review();
        review.setReviewId(10L);
        review.setMember(member);
        review.setBook(book);
        review.setGrade(4);
        review.setDescription("오늘도 나는 로또를 산다..");
        review.setCreatedAt(LocalDateTime.now());
    }

    /**
     * 테스트 시나리오: 성공적으로 리뷰를 등록하는 경우.
     * 조건:
     * - 유효한 memberId, bookId, 중복 리뷰 없음, 이미지 2장 첨부(정상 범위)
     * 결과:
     * - 리뷰가 정상 등록되고, 응답 DTO에 생성된 reviewId, 입력한 memberId, bookId, description, imageUrl이 담긴다.
     * - 포인트 적립 함수 호출 확인(사진첨부=true -> 500포인트)
     */
    @Test
    void testRegisterReviewSuccess() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 1L, 5, "개추", List.of("img1", "img2"));
        given(memberService.getMemberById(1L)).willReturn(member);
        given(bookService.getBook(1L)).willReturn(book);
        given(reviewRepository.findByMemberAndBook(member, book)).willReturn(Optional.empty());
        given(reviewRepository.save(any(Review.class))).willAnswer(invocation -> {
            Review saved = invocation.getArgument(0);
            saved.setReviewId(100L);
            return saved;
        });

        // 포인트 적립 mocking (이미지 2장 첨부 -> 사진첨부=true)
        willDoNothing().given(pointService).registerReviewPoints(member, true);

        // When
        ReviewResponse response = reviewService.registerReview(1L, request);

        // Then
        assertEquals(100L, response.getReviewId());
        assertEquals(1L, response.getMemberId());
        assertEquals(1L, response.getBookId());
        assertEquals("개추", response.getDescription());
        assertEquals(2, response.getImageUrl().size());
        assertTrue(response.getImageUrl().contains("img1"));
        assertTrue(response.getImageUrl().contains("img2"));
        verify(pointService, times(1)).registerReviewPoints(member, true);
    }

    /**
     * 테스트 시나리오: 이미 해당 도서에 리뷰를 작성한 경우 예외 발생.
     * 조건:
     * - memberId, bookId 유효
     * - 해당 member, book에 대한 리뷰가 이미 존재
     * 결과:
     * - "이미 해당 도서에 대한 리뷰를 작성하셨습니다." 예외 발생
     */
    @Test
    void testRegisterReviewAlreadyExists() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 1L, 5, "개추", null);
        given(memberService.getMemberById(1L)).willReturn(member);
        given(bookService.getBook(1L)).willReturn(book);
        given(reviewRepository.findByMemberAndBook(member, book)).willReturn(Optional.of(review));

        // When/Then
        ReviewAlreadyExistsException exception = assertThrows(ReviewAlreadyExistsException.class, () -> {
            reviewService.registerReview(1L, request);
        });
        assertEquals("이미 해당 도서에 대한 리뷰를 작성하셨습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 존재하지 않는 회원 ID로 리뷰 등록 시도.
     * 조건:
     * - memberId가 DB에 없음
     * 결과:
     * - "Member id does not exist" 예외 발생
     */
    @Test
    void testRegisterReviewInvalidMember() {
        // Given
        ReviewRequest request = new ReviewRequest(999L, 1L, 5, "Great book!", null);
        given(memberService.getMemberById(999L)).willThrow(new MemberIllegalArgumentException("Member id does not exist"));

        // When/Then
        MemberIllegalArgumentException exception = assertThrows(MemberIllegalArgumentException.class, () -> {
            reviewService.registerReview(1L, request);
        });
        assertEquals("Member id does not exist", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 존재하지 않는 도서 ID로 리뷰 등록 시도.
     * 조건:
     * - bookId가 DB에 없음
     * 결과:
     * - "Invalid book ID" 예외 발생
     */
    @Test
    void testRegisterReviewInvalidBook() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 999L, 5, "Great book!", null);
        given(memberService.getMemberById(1L)).willReturn(member);
        given(bookService.getBook(999L)).willThrow(new BookNotFoundException("Invalid book ID"));

        // When/Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            reviewService.registerReview(999L, request);
        });
        assertEquals("Invalid book ID", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 첨부 이미지가 4장으로 제한(3장) 초과하는 경우 예외 발생.
     * 조건:
     * - 이미지 리스트 크기 4
     * 결과:
     * - "이미지는 최대 3장까지 첨부할 수 있습니다." 예외 발생
     */
    @Test
    void testRegisterReviewImageOverLimit() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 1L, 5, "Great book!", List.of("img1", "img2", "img3", "img4"));
        given(memberService.getMemberById(1L)).willReturn(member);
        given(bookService.getBook(1L)).willReturn(book);
        given(reviewRepository.findByMemberAndBook(member, book)).willReturn(Optional.empty());

        // When/Then
        ImageLimitExceededException exception = assertThrows(ImageLimitExceededException.class, () -> {
            reviewService.registerReview(1L, request);
        });
        assertEquals("이미지는 최대 3장까지 첨부할 수 있습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 특정 도서에 대한 평균 평점 조회 성공.
     * 조건:
     * - 해당 도서에 리뷰 존재하여 평균 평점 4.5
     * 결과:
     * - 평균 평점 4.5 반환
     */
    @Test
    void testGetReviewGradeAverageFound() {
        // Given
        given(reviewRepository.findAverageGradeByBookId(1L)).willReturn(Optional.of(4.5));

        // When
        double avg = reviewService.getReviewGradeAverage(1L);

        // Then
        assertEquals(4.5, avg);
    }

    /**
     * 테스트 시나리오: 특정 도서에 리뷰가 없어서 평균 평점이 없는 경우.
     * 결과:
     * - 평균 평점 0.0 반환
     */
    @Test
    void testGetReviewGradeAverageNotFound() {
        // Given
        given(reviewRepository.findAverageGradeByBookId(1L)).willReturn(Optional.empty());

        // When
        double avg = reviewService.getReviewGradeAverage(1L);

        // Then
        assertEquals(0.0, avg);
    }

    /**
     * 테스트 시나리오: 특정 도서에 대한 리뷰 목록 페이징 조회.
     * 조건:
     * - 해당 도서에 리뷰 1개 존재
     * 결과:
     * - 반환된 Page 객체에 1개의 ReviewResponse 포함
     */
    @Test
    void testGetReviewByBook() {
        // Given
        Page<Review> reviewPage = new PageImpl<>(List.of(review));
        given(reviewRepository.findByBookBookId(eq(1L), any(Pageable.class))).willReturn(reviewPage);

        // When
        Page<ReviewResponse> responsePage = reviewService.getReviewByBook(1L, 0, 10);

        // Then
        assertEquals(1, responsePage.getTotalElements());
        ReviewResponse response = responsePage.getContent().get(0);
        assertEquals(10L, response.getReviewId());
        assertEquals(1L, response.getMemberId());
        assertEquals(1L, response.getBookId());
        assertEquals("오늘도 나는 로또를 산다..", response.getDescription());
        assertEquals(0, response.getImageUrl().size());
    }

    /**
     * 테스트 시나리오: 리뷰 수정 성공.
     * 조건:
     * - 리뷰 작성자와 수정 요청 memberId 일치
     * - 이미지 1장 첨부
     * 결과:
     * - 리뷰 정보 수정(grade, description, updatedAt, imageUrl)
     */
    @Test
    void testUpdateReviewSuccess() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 1L, 3, "Updated Description", List.of("img1"));
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));
        given(reviewRepository.save(any(Review.class))).willAnswer(invocation -> invocation.getArgument(0));

        // When
        ReviewResponse response = reviewService.updateReview(1L, 10L, request);

        // Then
        assertEquals(10L, response.getReviewId());
        assertEquals("Updated Description", response.getDescription());
        assertEquals(3, response.getGrade());
        assertNotNull(response.getUpdatedAt());
        assertEquals(1, response.getImageUrl().size());
        assertEquals("img1", response.getImageUrl().get(0));
    }

    /**
     * 테스트 시나리오: 다른 도서에 속한 리뷰를 수정 시도.
     * 조건:
     * - review의 bookId와 요청한 bookId 불일치
     * 결과:
     * - "리뷰가 해당 도서에 속하지 않습니다." 예외 발생
     */
    @Test
    void testUpdateReviewWrongBook() {
        // Given
        review.getBook().setBookId(2L);
        ReviewRequest request = new ReviewRequest(1L, 1L, 3, "Updated Description", null);
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When/Then
        InvalidReviewException exception = assertThrows(InvalidReviewException.class, () -> {
            reviewService.updateReview(1L, 10L, request);
        });
        assertEquals("리뷰가 해당 도서에 속하지 않습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 리뷰 작성자가 아닌 다른 사용자가 리뷰 수정 시도.
     * 조건:
     * - review.getMember().getId() != 요청한 memberId
     * 결과:
     * - "작성자만 리뷰를 수정할 수 있습니다." 예외 발생
     */
    @Test
    void testUpdateReviewNotAuthor() {
        // Given
        ReviewRequest request = new ReviewRequest(2L, 1L, 3, "Updated Description", null);
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When/Then
        InvalidReviewException exception = assertThrows(InvalidReviewException.class, () -> {
            reviewService.updateReview(1L, 10L, request);
        });
        assertEquals("작성자만 리뷰를 수정할 수 있습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 리뷰 수정 시 첨부 이미지 4장으로 제한 초과.
     * 조건:
     * - 이미지가 4장
     * 결과:
     * - "이미지는 최대 3장까지 첨부할 수 있습니다." 예외 발생
     */
    @Test
    void testUpdateReviewImageOverLimit() {
        // Given
        ReviewRequest request = new ReviewRequest(1L, 1L, 3, "Updated Description", List.of("img1", "img2", "img3", "img4"));
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When/Then
        ImageLimitExceededException exception = assertThrows(ImageLimitExceededException.class, () -> {
            reviewService.updateReview(1L, 10L, request);
        });
        assertEquals("이미지는 최대 3장까지 첨부할 수 있습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 관리자에 의한 리뷰 삭제
     * 조건:
     * - 관리자(role_id=2) 삭제 시도
     * - 해당 리뷰가 존재하고 도서 일치
     * 결과:
     * - 리뷰가 정상 삭제되고 삭제 전 정보 반환
     */
    @Test
    void testDeleteReviewByAdmin() {
        // Given
        ReviewRequest request = new ReviewRequest(2L, 1L, 4, "Delete this review", null);
        given(memberService.getMemberById(2L)).willReturn(adminMember);
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When
        ReviewResponse response = reviewService.deleteReview(1L, 10L, request);

        // Then
        assertEquals(10L, response.getReviewId());
        assertEquals(4, response.getGrade());
        assertEquals("오늘도 나는 로또를 산다..", response.getDescription());
        verify(reviewRepository, times(1)).delete(review);
    }

    /**
     * 테스트 시나리오: 일반 회원이 리뷰 삭제 시도
     * 조건:
     * - member.role_id != 2 (관리자가 아님)
     * - 리뷰를 삭제할 권한 없음
     * 결과:
     * - "해당 리뷰를 삭제할 권한이 없습니다." 예외 발생
     */
    @Test
    void testDeleteReviewByNonAdmin() throws Exception {
        // Given
        // member는 이미 role_id=1(Member)로 설정됨
        ReviewRequest request = new ReviewRequest(1L, 1L, 4, "Delete Attempt", null);
        given(memberService.getMemberById(1L)).willReturn(member);
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When/Then
        InvalidReviewException exception = assertThrows(InvalidReviewException.class, () -> {
            reviewService.deleteReview(1L, 10L, request);
        });
        assertEquals("해당 리뷰를 삭제할 권한이 없습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 존재하지 않는 리뷰 삭제 시도
     * 조건:
     * - reviewId가 존재하지 않음
     * 결과:
     * - "리뷰를 찾을 수 없습니다." 예외 발생
     */
    @Test
    void testDeleteReviewNotFound() {
        // Given (adminMember는 이미 존재)
        ReviewRequest request = new ReviewRequest(2L, 1L, 4, "Delete Attempt", null);
        given(memberService.getMemberById(2L)).willReturn(adminMember);
        given(reviewRepository.findById(999L)).willReturn(Optional.empty());

        // When/Then
        InvalidReviewException exception = assertThrows(InvalidReviewException.class, () -> {
            reviewService.deleteReview(1L, 999L, request);
        });
        assertEquals("리뷰를 찾을 수 없습니다.", exception.getMessage());
    }

    /**
     * 테스트 시나리오: 도서 불일치 시 리뷰 삭제 시도
     * 조건:
     * - review.getBook().getBookId() != 요청한 bookId
     * 결과:
     * - "리뷰가 해당 도서에 속하지 않습니다." 예외 발생
     */
    @Test
    void testDeleteReviewWrongBook() {
        // Given
        review.getBook().setBookId(2L); // 도서 불일치
        ReviewRequest request = new ReviewRequest(2L, 1L, 4, "Delete Attempt", null);
        given(memberService.getMemberById(2L)).willReturn(adminMember);
        given(reviewRepository.findById(10L)).willReturn(Optional.of(review));

        // When/Then
        InvalidReviewException exception = assertThrows(InvalidReviewException.class, () -> {
            reviewService.deleteReview(1L, 10L, request);
        });
        assertEquals("리뷰가 해당 도서에 속하지 않습니다.", exception.getMessage());
    }

}
