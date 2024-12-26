package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_coupons_for_category")
@Getter
public class RateCouponForCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateCouponForCategoryId;

    @Column(nullable = false)
    private String couponNumber;

    @Column(nullable = false)
    private LocalDateTime dateOfIssuance;

    private LocalDateTime dateOfUsage;

    @ManyToOne
    @JoinColumn(name = "ratePolicyForCategoryId", nullable = false)
    private RatePolicyForCategory ratePolicyForCategory;

    @ManyToOne
    @JoinColumn(name = "couponStatusId", nullable = false)
    private CouponStatus couponStatus;
}
