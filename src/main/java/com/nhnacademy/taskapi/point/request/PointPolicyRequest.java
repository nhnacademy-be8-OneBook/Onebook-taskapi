package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PointPolicyRequest(String pointPolicyName, Integer pointPolicyApply,  // Integer로 변경
                                 String pointPolicyCondition, boolean pointPolicyApplyType,
                                 Integer pointPolicyConditionAmount) {  // Integer로 변경

    public PointPolicy toEntity() {
        return PointPolicy.builder()

                .pointPolicyName(pointPolicyName)
                .pointPolicyApplyAmount(pointPolicyApplyType && pointPolicyApply != null ? pointPolicyApply : 0) // 기본값 0 사용
                .pointPolicyRate(!pointPolicyApplyType && pointPolicyApply != null ? pointPolicyApply : 0) // 기본값 0 사용
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyConditionAmount(!pointPolicyApplyType && pointPolicyConditionAmount != null ? pointPolicyConditionAmount : 0) // 기본값 0 사용
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyState(true) // 기본값 true로 설정
                .build();
    }
}