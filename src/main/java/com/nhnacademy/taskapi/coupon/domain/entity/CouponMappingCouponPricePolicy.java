package com.nhnacademy.taskapi.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_coupon_price_policies")
public class CouponMappingCouponPricePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingCouponPricePolicyId;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "couponPricePolicyId")
    private CouponPricePolicy couponPricePolicy;
}
