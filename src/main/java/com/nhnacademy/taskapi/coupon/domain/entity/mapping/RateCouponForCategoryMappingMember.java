package com.nhnacademy.taskapi.coupon.domain.entity.mapping;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.RateCouponForCategory;
import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;

@Entity
public class RateCouponForCategoryMappingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateCouponForCategoryMappingMemberId;

    @OneToOne
    @JoinColumn(name = "rate_coupon_for_category" , nullable = false)
    private RateCouponForCategory rateCouponForCategory;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
