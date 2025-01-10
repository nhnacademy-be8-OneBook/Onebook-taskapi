package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false)
    private String couponNumber;

    @ManyToOne
    @JoinColumn(name ="rate_policy_for_book_id")
    private RatePolicyForBook ratePolicyForBook;

    @ManyToOne
    @JoinColumn(name ="rate_policy_for_category_id")
    private RatePolicyForCategory ratePolicyForCategory;

    @ManyToOne
    @JoinColumn(name = "price_polciy_for_book_id")
    private PricePolicyForBook pricePolicyForBook;

    @ManyToOne
    @JoinColumn(name = "price_polciy_for_category_id")
    private PricePolicyForCategory pricePolicyForCategory;

    @ManyToOne
    @JoinColumn(name = "coupon_status", nullable = false)
    private CouponStatus couponStatus;
}
