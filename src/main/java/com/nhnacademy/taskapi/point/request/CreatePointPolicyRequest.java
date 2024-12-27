package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.member.domain.Member;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreatePointPolicyRequest(Long memberId, String pointPolicyName, int pointPolicyApply,
                                       String pointPolicyCondition, boolean pointPolicyApplyType,
                                       int pointPolicyConditionAmount) {

    public PointPolicy toEntity(Member member) {  // Member 객체를 받도록 수정
        return PointPolicy.builder()
                .member(member)  // Member 객체 추가
                .pointPolicyName(pointPolicyName)
                .pointPolicyApplyAmount(pointPolicyApplyType ? pointPolicyApply : 0)
                .pointPolicyRate(!pointPolicyApplyType ? pointPolicyApply : 0)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyConditionAmount(pointPolicyConditionAmount)
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyState(true)  // 기본값 true
                .build();
    }
}
