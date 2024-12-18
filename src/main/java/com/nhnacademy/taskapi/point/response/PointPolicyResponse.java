package com.nhnacademy.taskapi.point.response;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record PointPolicyResponse(String pointPolicyId, String pointPolicyName, int pointPolicyApply, String pointPolicyCondition,
                                  boolean pointPolicyApplyType, LocalDate pointPolicyCreatedAt, String pointPolicyUpdatedAt,
                                  int pointPolicyConditionAmount, boolean pointPolicyState) {

    public static PointPolicyResponse create(PointPolicy pointPolicy, CreatePointPolicyRequest policyRequest) {
        return PointPolicyResponse.builder()
                .pointPolicyId(String.valueOf(pointPolicy.getPointPolicyId()))
                .pointPolicyName(policyRequest.pointPolicyName())
                .pointPolicyApply(policyRequest.pointPolicyApply())
                .pointPolicyCondition(policyRequest.pointPolicyCondition())
                .pointPolicyApplyType(policyRequest.pointPolicyApplyType())
                .pointPolicyConditionAmount(policyRequest.pointPolicyConditionAmount())
                .pointPolicyState(true)
                .build();
    }
    public static PointPolicyResponse update(PointPolicy pointPolicy, PointPolicyRequest policyRequest) {
        return PointPolicyResponse.builder()
                .pointPolicyId(String.valueOf(pointPolicy.getPointPolicyId()))
                .pointPolicyName(policyRequest.pointPolicyName())
                .pointPolicyApply(policyRequest.pointPolicyApply())
                .pointPolicyCondition(policyRequest.pointPolicyCondition())
                .pointPolicyApplyType(policyRequest.pointPolicyApplyType())
                .pointPolicyConditionAmount(policyRequest.pointPolicyConditionAmount())
                .build();
    }
    public static PointPolicyResponse find(PointPolicy pointPolicy) {
        return PointPolicyResponse.builder()
                .pointPolicyId(String.valueOf(pointPolicy.getPointPolicyId()))
                .pointPolicyName(pointPolicy.getPointPolicyName())
                .pointPolicyApply(pointPolicy.isPointPolicyApplyType() ? pointPolicy.getPointPolicyApplyAmount() : pointPolicy.getPointPolicyRate())
                .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                .pointPolicyUpdatedAt(pointPolicy.getPointPolicyUpdatedAt() != null ? pointPolicy.getPointPolicyUpdatedAt().toString() : null)
                .pointPolicyState(pointPolicy.isPointPolicyState())
                .build();
    }
}