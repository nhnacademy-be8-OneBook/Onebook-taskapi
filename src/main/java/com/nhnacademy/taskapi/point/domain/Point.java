package com.nhnacademy.taskapi.point.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private int pointCurrent;  // 현재 포인트

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "point_policy_id", nullable = false)
    private PointPolicy pointPolicy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;  // ENUM 타입 필드 추가

    @Column(nullable = false)
    private String actionComment;  // 액션 설명 추가

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 생성일시 필드 추가

    @Builder
    public Point(Integer pointCurrent, ActionType actionType, String actionComment, Member member, LocalDateTime createdAt) {
        this.pointCurrent = pointCurrent;
        this.actionType = actionType;
        this.actionComment = actionComment;
        this.member = member;
        this.createdAt = createdAt;
    }

    public Point(int pointCurrent, Member member) {
        this.pointCurrent = pointCurrent;
        this.member = member;
        this.createdAt = LocalDateTime.now();  // 생성일시는 현재 시간으로 설정
    }

    // 포인트 값 갱신 메소드
    public void updatePointCurrent(int pointCurrent) {
        this.pointCurrent = pointCurrent;
    }

    // 포인트를 반환하는 메소드 (getter 역할)
    public int getAmount() {
        return this.pointCurrent;
    }

    // 포인트를 설정하는 메소드 (setter 역할)
    public void setAmount(int updatedAmount) {
        this.pointCurrent = updatedAmount;
    }

    public enum ActionType {
        EARN, DEDUCT, REFUND  // ENUM 값 정의
    }
}