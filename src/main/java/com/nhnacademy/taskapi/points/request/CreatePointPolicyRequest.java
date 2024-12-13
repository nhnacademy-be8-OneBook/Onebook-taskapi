package com.nhnacademy.taskapi.points.request;

import com.nhnacademy.taskapi.points.domain.PointPolicy;
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
                .pointPolicyApplyAmount(pointPolicyApplyType ? pointPolicyApply : null)
                .pointPolicyRate(!pointPolicyApplyType ? pointPolicyApply : null)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyConditionAmount(!pointPolicyApplyType ? pointPolicyConditionAmount : null)
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyState(true)
                .build();
    }
}
