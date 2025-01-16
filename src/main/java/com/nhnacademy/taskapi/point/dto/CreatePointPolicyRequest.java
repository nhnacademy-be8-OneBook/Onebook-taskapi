package com.nhnacademy.taskapi.point.dto;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatePointPolicyRequest {
    private String pointPolicyName;
    private int pointPolicyRate;
    private Integer pointPolicyConditionAmount;  // Integer로 변경(null O)
    private Integer pointPolicyApplyAmount;      // Integer로 변경(null O)
    private String pointPolicyCondition;
    private boolean pointPolicyApplyType;

    public PointPolicy toEntity() {
        return PointPolicy.builder()
                .pointPolicyName(this.pointPolicyName)
                .pointPolicyConditionAmount(this.pointPolicyConditionAmount)
                .pointPolicyRate(this.pointPolicyRate)
                .pointPolicyApplyAmount(this.pointPolicyApplyAmount)
                .pointPolicyCondition(this.pointPolicyCondition)
                .pointPolicyApplyType(this.pointPolicyApplyType)
                .pointPolicyCreatedAt(LocalDateTime.now()) // 생성일 현재 시간
                .pointPolicyState(true) // 기본적으로 활성 상태
                .build();
    }
}