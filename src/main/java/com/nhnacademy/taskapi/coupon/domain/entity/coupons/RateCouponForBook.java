package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_coupons_for_book")
@Getter
public class RateCouponForBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateCouponForBookId;

    @Column(nullable = false)
    private String couponNumber;

    @Column(nullable = false)
    private LocalDateTime dateOfIssuance;

    private LocalDateTime dateOfUsage;

    @ManyToOne
    @JoinColumn(name = "rate_policy_for_book_id", nullable = false)
    private PricePolicyForBook ratePolicyForBook;

    @ManyToOne
    @JoinColumn(name = "coupon_status_id", nullable = false)
    private CouponStatus couponStatus;
}
