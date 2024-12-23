package com.nhnacademy.taskapi.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_rate_policies")
public class CouponMappingRatePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingRatePolicyId;

    @OneToOne
    @JoinColumn(name = "couponId" ,nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "ratePolicyId", nullable = false)
    private RatePolicy retePolicy;
}
