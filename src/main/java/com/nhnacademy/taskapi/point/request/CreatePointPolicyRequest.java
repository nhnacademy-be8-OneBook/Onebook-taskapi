package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreatePointPolicyRequest(Long memberId, String pointPolicyName, int pointPolicyApply,
                                       String pointPolicyCondition, boolean pointPolicyApplyType,
                                       int pointPolicyConditionAmount) {

    public PointPolicy toEntity() {
        return PointPolicy.builder()
                .memberId(memberId)  // Add memberId to PointPolicy
                .pointPolicyName(pointPolicyName)
                .pointPolicyApplyAmount(pointPolicyApplyType ? pointPolicyApply : 0)  // 0으로 기본값 설정
                .pointPolicyRate(!pointPolicyApplyType ? pointPolicyApply : 0)  // 0으로 기본값 설정
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyConditionAmount(pointPolicyConditionAmount)  // null 대신 조건값 그대로 사용
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyState(true)  // 기본값 true
                .build();
    }
}
