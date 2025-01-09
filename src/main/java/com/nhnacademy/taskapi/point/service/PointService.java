package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointService {

    // 회원가입 시 포인트 적립
    void registerMemberPoints(Member member);

    // 리뷰 작성 시 포인트 적립 (사진 첨부 여부 추가)
    void registerReviewPoints(Member member, boolean isPhotoAttached);

    // 도서 결제 시 포인트 적립
    void registerPurchasePoints(Member member, int purchaseAmount);

    // 포인트 정책 수정
    void updatePointPolicy(Long pointPolicyId, PointPolicy pointPolicy);

    // 포인트 결제 기능 (포인트 사용)
    void usePointsForPayment(String memberId, int paymentAmount);

    // 포인트 환불 기능 (포인트 복구)
    void updatePointByRefund(String memberId, int refundAmount);

    // 회원의 전체 포인트 조회
    Point getMemberPoints(Long memberId);

    // 특정 회원의 포인트 변동 내역 조회 (기간 등 필터링 가능)
    List<PointLog> getPointLogsByMember(Long memberId);

    List<PointLog> getPointLogsByMember(Long memberId, Pageable pageable);

    List<PointPolicy> getActivePointPolicies(Pageable pageable);

    // 포인트 정책 활성화/비활성화
    void togglePointPolicy(Long pointPolicyId, boolean isActive);

    // 모든 활성화된 포인트 정책 조회
    List<PointPolicy> getActivePointPolicies();
}






//사진 첨부된 경우 500포인트 적립
//pointService.registerReviewPoints(member, true);
//사진 첨부되지 않은 경우 200포인트 적립
//pointService.registerReviewPoints(member, false);