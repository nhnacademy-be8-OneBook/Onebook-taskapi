package com.nhnacademy.taskapi.coupon.domain.entity.mapping;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.RateCouponForBook;
import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;

@Entity
public class PriceCouponForBookMappingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceCouponForBookMappingMemberId;

    @OneToOne
    @JoinColumn(name = "price_coupon_for_book" , nullable = false)
    private RateCouponForBook priceCouponForBook;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
