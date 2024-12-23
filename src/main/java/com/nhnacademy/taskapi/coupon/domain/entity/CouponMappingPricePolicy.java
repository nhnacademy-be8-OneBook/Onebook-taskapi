package com.nhnacademy.taskapi.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_price_policies")
public class CouponMappingPricePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingPricePolicyId;

    @OneToOne
    @JoinColumn(name = "couponId",nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "pricePolicyId",nullable = false)
    private PricePolicy pricePolicy;
}
