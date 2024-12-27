package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_coupons_for_category")
@Getter
public class PriceCouponForCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceCouponForCategoryId;

    @Column(nullable = false)
    private String couponNumber;

    @Column(nullable = false)
    private LocalDateTime dateOfIssuance;

    private LocalDateTime dateOfUsage;

    @ManyToOne
    @JoinColumn(name = "price_policy_for_category_id", nullable = false)
    private PricePolicyForCategory pricePolicyForCategory;

    @ManyToOne
    @JoinColumn(name = "coupon_status_id", nullable = false)
    private CouponStatus couponStatus;
}
