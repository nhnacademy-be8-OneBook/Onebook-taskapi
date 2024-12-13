package com.nhnacademy.taskapi.point.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private int pointCurrent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Builder
    public Point(int pointCurrent, Member member) {
        this.pointCurrent = pointCurrent;
        this.member = member;
    }

    public void updatePointCurrent(int pointCurrent) {
        this.pointCurrent = pointCurrent;
    }
}
