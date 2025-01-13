package com.nhnacademy.taskapi.coupon.service.coupons;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.IssueCouponToMemberRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.IssuedCouponResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.Coupon;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.IssuedCoupon;
import com.nhnacademy.taskapi.coupon.exception.CouponNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponBoxRepository;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponRepository;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponBoxService {

    private final CouponBoxRepository couponBoxRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public IssuedCouponResponse issueCouponToMember(IssueCouponToMemberRequest issueCouponToMemberRequest){

        Member member = memberRepository.
                findById(issueCouponToMemberRequest.getMemberId()).orElseThrow
                        (()->new MemberNotFoundException("해당하는 ID의 멤버가 존재하지 않습니다"));

        Coupon coupon = couponRepository.findByCouponNumber(
                issueCouponToMemberRequest.getCouponNumber())
                .orElseThrow(()->new CouponNotFoundException("해당하는 번호의 쿠폰을 찾을 수 없습니다"));

        IssuedCoupon issuedCoupon = couponBoxRepository.save(IssuedCoupon.createIssuedCoupon(coupon,member));

        return IssuedCouponResponse.changeEntityToDto(issuedCoupon);
    }
}
