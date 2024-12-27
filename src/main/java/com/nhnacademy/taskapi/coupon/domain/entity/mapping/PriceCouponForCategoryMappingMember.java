package com.nhnacademy.taskapi.coupon.domain.entity.mapping;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.PriceCouponForCategory;
import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PriceCouponForCategoryMappingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceCouponForCategoryMappingMemberId;

    @OneToOne
    @JoinColumn(name = "price_coupon_for_category" , nullable = false)
    private PriceCouponForCategory priceCouponForCategory;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
