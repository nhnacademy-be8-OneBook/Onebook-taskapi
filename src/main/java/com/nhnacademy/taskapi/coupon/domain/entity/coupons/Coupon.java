package com.nhnacademy.taskapi.coupon.domain.entity.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false, unique = true)
    private String couponNumber;

    @Column(nullable = false)
    private LocalDateTime couponCreationDatetime;

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
    @JoinColumn(name = "coupon_status_id", nullable = false)
    private CouponStatus couponStatus;

    public Coupon(String couponNumber, RatePolicyForBook ratePolicyForBook, CouponStatus couponStatus, LocalDateTime creationTime) {
        this.couponNumber = couponNumber;
        this.ratePolicyForBook = ratePolicyForBook;
        this.couponCreationDatetime = creationTime;
        this.couponStatus = couponStatus;
    }

    public Coupon(String couponNumber, RatePolicyForCategory ratePolicyForCategory, CouponStatus couponStatus, LocalDateTime creationTime) {
        this.couponNumber = couponNumber;
        this.ratePolicyForCategory = ratePolicyForCategory;
        this.couponCreationDatetime = creationTime;
        this.couponStatus = couponStatus;
    }

    public Coupon(String couponNumber, PricePolicyForBook pricePolicyForBook, CouponStatus couponStatus, LocalDateTime creationTime) {
        this.couponNumber = couponNumber;
        this.pricePolicyForBook = pricePolicyForBook;
        this.couponCreationDatetime = creationTime;
        this.couponStatus = couponStatus;
    }

    public Coupon(String couponNumber, PricePolicyForCategory pricePolicyForCategory, CouponStatus couponStatus, LocalDateTime creationTime) {
        this.couponNumber = couponNumber;
        this.pricePolicyForCategory = pricePolicyForCategory;
        this.couponCreationDatetime = creationTime;
        this.couponStatus = couponStatus;
    }

    public static Coupon createRateCouponForBook(RatePolicyForBook ratePolicyForBook, CouponStatus unUsedStatus, LocalDateTime creationTime){
        return new Coupon(
                UUID.randomUUID().toString(),
                ratePolicyForBook,
                unUsedStatus,
                creationTime
        );
    }

    public static Coupon createPriceCouponForBook(PricePolicyForBook pricePolicyForBook, CouponStatus unUsedStatus, LocalDateTime creationTime){
        return new Coupon(
                UUID.randomUUID().toString(),
                pricePolicyForBook,
                unUsedStatus,
                creationTime
        );
    }

    public static Coupon createRateCouponForCategory(RatePolicyForCategory ratePolicyForCategory, CouponStatus unUsedStatus, LocalDateTime creationTime){
        return new Coupon(
                UUID.randomUUID().toString(),
                ratePolicyForCategory,
                unUsedStatus,
                creationTime
        );
    }

    public static Coupon createPriceCouponForCategory(PricePolicyForCategory pricePolicyForCategory , CouponStatus unUsedStatus, LocalDateTime creationTime){
        return new Coupon(
                UUID.randomUUID().toString(),
                pricePolicyForCategory,
                unUsedStatus,
                creationTime
        );
    }
}
