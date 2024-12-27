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
    @JoinColumn(name = "price_polciy_for_book_id", nullable = false)
    private PricePolicyForBook pricePolicyForBook;

    @ManyToOne
    @JoinColumn(name = "coupon_status", nullable = false)
    private CouponStatus couponStatus;
}
