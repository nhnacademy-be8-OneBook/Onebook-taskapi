package com.nhnacademy.taskapi.point.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointPolicyRequest {
    private String pointPolicyName;
    private Integer pointPolicyRate;
    private Integer pointPolicyConditionAmount;
    private Integer pointPolicyApplyAmount;
    private String pointPolicyCondition;
    private boolean pointPolicyApplyType;

    // 동일한 toEntity 메서드를 사용
    public PointPolicy toEntity() {
        return PointPolicy.builder()
                .pointPolicyName(this.pointPolicyName)
                .pointPolicyRate(this.pointPolicyRate != null ? this.pointPolicyRate : 0)  // null 체크 후 0으로 대체
                .pointPolicyConditionAmount(this.pointPolicyConditionAmount != null ? this.pointPolicyConditionAmount : 0)  // null 체크 후 0으로 대체
                .pointPolicyApplyAmount(this.pointPolicyApplyAmount != null ? this.pointPolicyApplyAmount : 0)  // null 체크 후 0으로 대체
                .pointPolicyCondition(this.pointPolicyCondition)  // null은 그대로 null로 처리
                .pointPolicyApplyType(this.pointPolicyApplyType)  // 기본값 처리
                .pointPolicyState(true)  // 기본값 활성 상태
                .pointPolicyCreatedAt(LocalDateTime.now()) // 생성일 현재 시간
                .build();
    }

    // 명시적인 getter 메서드들 (getter는 @Getter로 자동 생성되지만, 명시적으로 구현할 수도 있음)
    public String pointPolicyName() {
        return pointPolicyName;
    }

    public Integer pointPolicyApplyAmount() {
        return pointPolicyApplyAmount;
    }

    public Integer pointPolicyRate() {
        return pointPolicyRate;
    }

    public Integer pointPolicyConditionAmount() {
        return pointPolicyConditionAmount;
    }

    public String pointPolicyCondition() {
        return pointPolicyCondition;
    }

    public boolean pointPolicyApplyType() {
        return pointPolicyApplyType;
    }
}
