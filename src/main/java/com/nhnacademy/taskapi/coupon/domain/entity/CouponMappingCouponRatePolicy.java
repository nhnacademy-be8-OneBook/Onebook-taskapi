package com.nhnacademy.taskapi.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_coupon_rate_policies")
public class CouponMappingCouponRatePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingCouponRatePolicyId;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "couponPricePolicyId")
    private CouponRatePolicy couponPricePolicy;
}
