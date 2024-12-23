package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CouponForIssuedMappingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private CouponForIssuance couponForIssuance;

    @ManyToOne
    @JoinColumn(name = "id")
    private Member member;
}
