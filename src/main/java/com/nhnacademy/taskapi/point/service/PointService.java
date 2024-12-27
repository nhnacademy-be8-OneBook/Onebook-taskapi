package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.PointPolicy;

public interface PointService {
    // 회원가입 시 포인트 적립
    void registerMemberPoints(Member member);

    // 리뷰 작성 시 포인트 적립 (사진 첨부 여부 추가)
    void registerReviewPoints(Member member, boolean isPhotoAttached);

    // 도서 결제 시 포인트 적립
    void registerPurchasePoints(Member member, int purchaseAmount);

    // 포인트 정책 수정
    void updatePointPolicy(Long pointPolicyId, PointPolicy pointPolicy);
}

//사진 첨부된 경우 500포인트 적립
//pointService.registerReviewPoints(member, true);
//사진 첨부되지 않은 경우 200포인트 적립
//pointService.registerReviewPoints(member, false);