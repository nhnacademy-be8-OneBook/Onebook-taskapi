package com.nhnacademy.taskapi.point.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_policies")
public class PointPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointPolicyId;  // pointPolicyId는 Long 타입으로 유지

    @NotNull(message = "포인트 정책명은 필수입니다.")
    @Column(nullable = false, length = 50)
    private String pointPolicyName;

    @Column(nullable = true, length = 255)
    private String value;  // 'value' 필드 추가 (포인트 정책 값을 설명)

    @Column(length = 200)
    private String description;  // 'description' 필드 추가

    private Integer pointPolicyConditionAmount;  // Integer로 수정
    private Integer pointPolicyRate;  // Integer로 수정
    private Integer pointPolicyApplyAmount;  // Integer로 수정

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
        this.pointPolicyId = pointPolicyId;
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

    // Getter와 Setter 메소드들

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
