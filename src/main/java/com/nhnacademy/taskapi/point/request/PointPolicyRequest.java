package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder

public record PointPolicyRequest(String pointPolicyName, BigDecimal pointPolicyApply,
                                 String pointPolicyCondition, boolean pointPolicyApplyType,
                                 BigDecimal pointPolicyConditionAmount) {

    public PointPolicy toEntity() {
        return PointPolicy.builder()
                .pointPolicyName(pointPolicyName)
                .pointPolicyApplyAmount(pointPolicyApplyType ? pointPolicyApply : null)
                .pointPolicyRate(!pointPolicyApplyType ? pointPolicyApply : null)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyConditionAmount(!pointPolicyApplyType ? pointPolicyConditionAmount : null)
                .pointPolicyApplyType(pointPolicyApplyType)
                .build();
    }
}