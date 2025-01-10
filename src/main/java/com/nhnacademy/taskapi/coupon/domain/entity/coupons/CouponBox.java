package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupon_box")
public class CouponBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponBoxId;

    @ManyToOne
    @JoinColumn(name = "coupon_id",nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    private LocalDateTime issueDateTime;
    private LocalDateTime useDateTime;
    
}
