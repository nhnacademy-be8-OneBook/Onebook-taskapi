package com.nhnacademy.taskapi.points.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointPolicyId;

    @NotNull(message = "포인트 정책명은 필수입니다.")
    @Column(nullable = false, length = 50)
    private String pointPolicyName;

    private int pointPolicyConditionAmount;
    private int pointPolicyRate;
    private int pointPolicyApplyAmount;

    @NotNull(message = "포인트 적립 조건은 필수입니다.")
    @Column(nullable = false, length = 200)
    private String pointPolicyCondition;

    @NotNull(message = "포인트 적립 유형은 필수입니다.")
    @Column(nullable = false)
    private boolean pointPolicyApplyType;

    @NotNull(message = "포인트 생성일은 필수입니다.")
    @Column(nullable = false)
    private LocalDate pointPolicyCreatedAt;

    private LocalDate pointPolicyUpdatedAt;

    @NotNull(message = "포인트 상태는 필수입니다.")
    @Column(nullable = false)
    private boolean pointPolicyState;

    @NotNull(message = "회원 ID는 필수입니다.")
    @Column(nullable = false)
    private Long memberId;  // Add memberId field

    @Builder
    public PointPolicy(Long pointPolicyId, String pointPolicyName, int pointPolicyRate, int pointPolicyConditionAmount,
                       String pointPolicyCondition, int pointPolicyApplyAmount, LocalDate pointPolicyCreatedAt,
                       LocalDate pointPolicyUpdatedAt, boolean pointPolicyApplyType, boolean pointPolicyState, Long memberId) {
        this.pointPolicyId = pointPolicyId;
        this.pointPolicyName = pointPolicyName;
        this.pointPolicyConditionAmount = pointPolicyConditionAmount;
        this.pointPolicyRate = pointPolicyRate;
        this.pointPolicyCondition = pointPolicyCondition;
        this.pointPolicyApplyAmount = pointPolicyApplyAmount;
        this.pointPolicyApplyType = pointPolicyApplyType;
        this.pointPolicyCreatedAt = pointPolicyCreatedAt;
        this.pointPolicyUpdatedAt = pointPolicyUpdatedAt;
        this.pointPolicyState = pointPolicyState;
        this.memberId = memberId; // Set memberId
    }

    public void updateMemberId(Long memberId) {
        this.memberId = memberId;
    }

    // 기타 업데이트 메서드들
    public void updatePointPolicyName(String pointPolicyName) {
        this.pointPolicyName = pointPolicyName;
    }

    public void updatePointPolicyConditionAmount(Integer pointPolicyConditionAmount) {
        this.pointPolicyConditionAmount = pointPolicyConditionAmount;
    }

    public void updatePointPolicyApplyAmount(Integer pointPolicyApplyAmount) {
        this.pointPolicyApplyAmount = pointPolicyApplyAmount;
    }

    public void updatePointPolicyRate(Integer pointPolicyRate) {
        this.pointPolicyRate = pointPolicyRate;
    }

    public void updatePointPolicyCondition(String pointPolicyCondition) {
        this.pointPolicyCondition = pointPolicyCondition;
    }

    public void updatePointPolicyApplyType(boolean pointPolicyApplyType) {
        this.pointPolicyApplyType = pointPolicyApplyType;
    }

    public void updatePointPolicyUpdatedAt() {
        this.pointPolicyUpdatedAt = LocalDate.now();
    }

    public void updatePointPolicyState(boolean pointPolicyState) {
        this.pointPolicyState = pointPolicyState;
    }
}