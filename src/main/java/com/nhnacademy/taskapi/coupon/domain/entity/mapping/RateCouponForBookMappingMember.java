package com.nhnacademy.taskapi.coupon.domain.entity.mapping;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.RateCouponForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.RateCouponForCategory;
import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RateCouponForBookMappingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateCouponForBookMappingMemberId;

    @OneToOne
    @JoinColumn(name = "rate_coupon_for_book" , nullable = false)
    private RateCouponForBook rateCouponForBook;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
