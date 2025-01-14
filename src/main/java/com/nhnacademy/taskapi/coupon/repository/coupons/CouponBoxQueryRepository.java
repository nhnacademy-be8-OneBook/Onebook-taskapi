package com.nhnacademy.taskapi.coupon.repository.coupons;

import com.nhnacademy.taskapi.coupon.domain.entity.coupons.IssuedCoupon;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.QIssuedCoupon;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import com.nhnacademy.taskapi.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class CouponBoxQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<IssuedCoupon> getValidIssuedCoupon(Long memberId, CouponStatus couponStatus, Pageable pageable){

        QIssuedCoupon issuedCoupon = QIssuedCoupon.issuedCoupon;

        List<IssuedCoupon> content = jpaQueryFactory
                .selectFrom(issuedCoupon)
                .where(
                        issuedCoupon.member.id.eq(memberId),
                        issuedCoupon.useDateTime.isNull(),
                        issuedCoupon.coupon.couponStatus.ne(couponStatus)
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(issuedCoupon)
                .where(
                        issuedCoupon.member.id.eq(memberId),
                        issuedCoupon.useDateTime.isNull(),
                        issuedCoupon.coupon.couponStatus.ne(couponStatus)
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .stream().count();

        return new PageImpl<>(content,pageable,total);
    }
}
