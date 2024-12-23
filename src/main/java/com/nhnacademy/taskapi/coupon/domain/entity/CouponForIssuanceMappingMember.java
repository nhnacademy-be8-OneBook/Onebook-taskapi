package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_for_issuance_mapping_members")
public class CouponForIssuanceMappingMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponForIssuanceMappingMemberId;

    @ManyToOne
    @JoinColumn(name = "couponForIssuanceId")
    private CouponForIssuance couponForIssuance;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
