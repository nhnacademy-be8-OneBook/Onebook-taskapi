package com.nhnacademy.taskapi.point.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor  // JPA 엔티티 클래스에서는 기본 생성자가 필요
@Getter
@EqualsAndHashCode(of = "pointPolicyId")  // pointPolicyId 기준으로 equals, hashCode 생성
@Table(name = "point_policies")
public class PointPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointPolicyId;

    @NotNull(message = "포인트 정책명은 필수입니다.")
    @Column(nullable = false, length = 50)
    private String pointPolicyName;

    @Column(length = 255)
    private String value;  // 'value' 필드 추가 (포인트 정책 값을 설명)

    @Column(length = 200)
    private String description;  // 'description' 필드 추가

    private Integer pointPolicyConditionAmount;
    private Integer pointPolicyRate;
    private Integer pointPolicyApplyAmount;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // Member와의 관계 설정

    @OneToMany(mappedBy = "pointPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();  // Point와의 관계 (양방향)

    @Builder
    public PointPolicy(Long pointPolicyId, String pointPolicyName, String value, String description,
                       Integer pointPolicyRate, Integer pointPolicyConditionAmount, String pointPolicyCondition,
                       Integer pointPolicyApplyAmount, LocalDate pointPolicyCreatedAt, LocalDate pointPolicyUpdatedAt,
                       boolean pointPolicyApplyType, boolean pointPolicyState, Member member) {
        this.pointPolicyId = pointPolicyId; // pointPolicyId is auto-generated, so it will be null initially
        this.pointPolicyName = pointPolicyName;
        this.value = value;
        this.description = description;
        this.pointPolicyRate = pointPolicyRate;
        this.pointPolicyConditionAmount = pointPolicyConditionAmount;
        this.pointPolicyCondition = pointPolicyCondition;
        this.pointPolicyApplyAmount = pointPolicyApplyAmount;
        this.pointPolicyCreatedAt = pointPolicyCreatedAt;
        this.pointPolicyUpdatedAt = pointPolicyUpdatedAt;
        this.pointPolicyApplyType = pointPolicyApplyType;
        this.pointPolicyState = pointPolicyState;
        this.member = member;
    }

    // 포인트 정책 상태 변경 메소드 (활성화, 비활성화)
    public void changeState(boolean state) {
        this.pointPolicyState = state;
    }

    // 포인트 정책이 활성화 상태인지 확인하는 메소드
    public boolean isActive() {
        return this.pointPolicyState;
    }

    // 조건 금액 또는 비율을 적용할 수 있도록 설정하는 메소드
    public void applyPolicyAmountOrRate() {
        if (pointPolicyApplyType) {
            // 적립 금액을 적용
            this.pointPolicyApplyAmount = pointPolicyRate != null ? pointPolicyRate : 0;
        } else {
            // 적립 비율을 적용
            this.pointPolicyRate = pointPolicyConditionAmount != null ? pointPolicyConditionAmount : 0;
        }
    }

    // 포인트 정책 이름 업데이트 메소드
    public void updatePointPolicyName(String pointPolicyName) {
        this.pointPolicyName = pointPolicyName;
    }

    // 포인트 정책 조건 금액 업데이트 메소드
    public void updatePointPolicyConditionAmount(Integer pointPolicyConditionAmount) {
        this.pointPolicyConditionAmount = pointPolicyConditionAmount;
    }

    // 포인트 정책 적립 비율 업데이트 메소드
    public void updatePointPolicyRate(Integer pointPolicyRate) {
        this.pointPolicyRate = pointPolicyRate;
    }

    // 포인트 정책 적립 금액 업데이트 메소드
    public void updatePointPolicyApplyAmount(Integer pointPolicyApplyAmount) {
        this.pointPolicyApplyAmount = pointPolicyApplyAmount;
    }

    // 포인트 정책 조건 업데이트 메소드
    public void updatePointPolicyCondition(String pointPolicyCondition) {
        this.pointPolicyCondition = pointPolicyCondition;
    }

    // 포인트 정책 적용 유형 업데이트 메소드
    public void updatePointPolicyApplyType(boolean pointPolicyApplyType) {
        this.pointPolicyApplyType = pointPolicyApplyType;
    }

    // 포인트 정책 수정일 업데이트 메소드
    public void updatePointPolicyUpdatedAt() {
        this.pointPolicyUpdatedAt = LocalDate.now();
    }

    // 포인트 정책 상태 업데이트 메소드
    public void updatePointPolicyState(boolean pointPolicyState) {
        this.pointPolicyState = pointPolicyState;
    }

    // 엔티티가 저장되기 전에 자동으로 호출되어 날짜를 설정
    @PrePersist
    public void prePersist() {
        this.pointPolicyCreatedAt = LocalDate.now();  // 생성일 설정
        this.pointPolicyUpdatedAt = LocalDate.now();  // 생성일과 수정일을 동일하게 설정
    }

    // 엔티티가 업데이트되기 전에 호출되어 수정일을 자동 갱신
    @PreUpdate
    public void preUpdate() {
        this.pointPolicyUpdatedAt = LocalDate.now();  // 수정일 자동 갱신
    }

    @Override
    public String toString() {
        return "PointPolicy{" +
                "pointPolicyId=" + pointPolicyId +
                ", pointPolicyName='" + pointPolicyName + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", pointPolicyConditionAmount=" + pointPolicyConditionAmount +
                ", pointPolicyRate=" + pointPolicyRate +
                ", pointPolicyApplyAmount=" + pointPolicyApplyAmount +
                ", pointPolicyCondition='" + pointPolicyCondition + '\'' +
                ", pointPolicyApplyType=" + pointPolicyApplyType +
                ", pointPolicyCreatedAt=" + pointPolicyCreatedAt +
                ", pointPolicyUpdatedAt=" + pointPolicyUpdatedAt +
                ", pointPolicyState=" + pointPolicyState +
                '}';
    }
}