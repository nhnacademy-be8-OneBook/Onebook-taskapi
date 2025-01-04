package com.nhnacademy.taskapi.point.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private int pointCurrent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "point_policy_id")
    private PointPolicy pointPolicy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Builder
    public Point(int pointCurrent, PointPolicy pointPolicy, Member member) {
        this.pointCurrent = pointCurrent;
        this.pointPolicy = pointPolicy;
        this.member = member;
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
}