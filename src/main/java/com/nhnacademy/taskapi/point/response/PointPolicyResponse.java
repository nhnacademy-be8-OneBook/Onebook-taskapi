package com.nhnacademy.taskapi.point.response;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import lombok.Builder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record PointPolicyResponse(String pointPolicyId, String pointPolicyName, int pointPolicyApply,
                                  String pointPolicyCondition, boolean pointPolicyApplyType,
                                  LocalDate pointPolicyCreatedAt, String pointPolicyUpdatedAt,
                                  int pointPolicyConditionAmount, boolean pointPolicyState) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 공통 로직을 별도의 메소드로 처리
    private static String formatDate(LocalDate date) {
        return (date != null) ? date.format(DATE_FORMATTER) : null;
    }

    private static int determineApplyAmount(PointPolicy pointPolicy) {
        return pointPolicy.isPointPolicyApplyType()
                ? pointPolicy.getPointPolicyApplyAmount()
                : pointPolicy.getPointPolicyRate();
    }

    public static PointPolicyResponse create(PointPolicy pointPolicy, CreatePointPolicyRequest policyRequest) {
        return PointPolicyResponse.builder()
                .pointPolicyId(String.valueOf(pointPolicy.getPointPolicyId()))
                .pointPolicyName(policyRequest.pointPolicyName())
                .pointPolicyApply(policyRequest.pointPolicyApply())
                .pointPolicyCondition(policyRequest.pointPolicyCondition())
                .pointPolicyApplyType(policyRequest.pointPolicyApplyType())
                .pointPolicyConditionAmount(policyRequest.pointPolicyConditionAmount())
                .pointPolicyState(pointPolicy.isPointPolicyState()) // 실제 상태로 설정
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
                .pointPolicyState(pointPolicy.isPointPolicyState()) // 실제 상태로 설정
                .build();
    }

    // 공통된 부분을 처리하여 반복 감소
    public static PointPolicyResponse find(PointPolicy pointPolicy) {
        return PointPolicyResponse.builder()
                .pointPolicyId(String.valueOf(pointPolicy.getPointPolicyId()))
                .pointPolicyName(pointPolicy.getPointPolicyName())
                .pointPolicyApply(determineApplyAmount(pointPolicy))  // 조건에 따라 값 결정
                .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                .pointPolicyUpdatedAt(formatDate(pointPolicy.getPointPolicyUpdatedAt()))  // 날짜 포맷팅
                .pointPolicyState(pointPolicy.isPointPolicyState())
                .build();
    }
}
