package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_coupons_for_book")
@Getter
public class PriceCouponForBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceCouponForBookId;

    @Column(nullable = false)
    private String couponNumber;

    @Column(nullable = false)
    private LocalDateTime dateOfIssuance;

    private LocalDateTime dateOfUsage;

    @ManyToOne
    @JoinColumn(name = "pricePolicyForBookId", nullable = false)
    private PricePolicyForBook pricePolicyForBook;

    @ManyToOne
    @JoinColumn(name = "couponStatusId", nullable = false)
    private CouponStatus couponStatus;
}
