package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreatePointPolicyRequest(
        Long memberId,
        String pointPolicyName,
        int pointPolicyApply,
        String pointPolicyCondition,
        boolean pointPolicyApplyType,
        int pointPolicyConditionAmount) {

    // Member 객체를 받아 PointPolicy 엔티티로 변환하는 메서드
    public PointPolicy toEntity(Member member) {
        return PointPolicy.builder()
                .member(member)  // Member 객체 전달
                .pointPolicyName(pointPolicyName)
                .pointPolicyApplyAmount(pointPolicyApplyType ? pointPolicyApply : 0)
                .pointPolicyRate(!pointPolicyApplyType ? pointPolicyApply : 0)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())  // 현재 날짜 설정
                .pointPolicyConditionAmount(pointPolicyConditionAmount)
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyState(true)  // 기본값 true (활성 상태)
                .build();
    }
}