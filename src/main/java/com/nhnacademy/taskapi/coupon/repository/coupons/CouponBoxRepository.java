package com.nhnacademy.taskapi.coupon.repository.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.IssuedCoupon;
import com.nhnacademy.taskapi.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponBoxRepository extends JpaRepository<IssuedCoupon, Long> {
    Page<IssuedCoupon> findByMemberAndCoupon_CouponStatus_Name(Member member, String couponCouponStatusName, Pageable pageable);

    Page<IssuedCoupon> findByMemberAndUseDateTimeIsNull(Member member, Pageable pageable);
}
