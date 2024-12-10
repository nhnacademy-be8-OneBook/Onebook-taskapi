package com.nhnacademy.taskapi.point;

import com.nhnacademy.taskapi.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pointCurrent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Builder
    public Point(BigDecimal pointCurrent, Member member) {
        //jkkj
        this.pointCurrent = pointCurrent;
        this.member = member;
    }

    public void updatePointCurrent(BigDecimal pointCurrent) {
        this.pointCurrent = pointCurrent;
    }
}
