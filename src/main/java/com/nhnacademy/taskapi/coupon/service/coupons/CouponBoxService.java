package com.nhnacademy.taskapi.coupon.service.coupons;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.IssueCouponToMemberRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.CouponResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.IssuedCouponResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.Coupon;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.IssuedCoupon;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import com.nhnacademy.taskapi.coupon.exception.CouponNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponBoxRepository;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponBoxRepositoryImpl;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponRepository;
import com.nhnacademy.taskapi.coupon.repository.status.CouponStatusRepository;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponBoxService {

    private final CouponBoxRepositoryImpl couponBoxRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    private final CouponService couponService;
    private final CouponStatusRepository couponStatusRepository;

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

    public IssuedCouponResponse issueWelcomeCouponToMember(String loginId){

        CouponResponse couponResponse = couponService.createWelcomeCoupon();

        Coupon welcomeCoupon = couponRepository
                .findByCouponNumber(couponResponse.getCouponNumber())
                .orElseThrow(()->new CouponNotFoundException("해당하는 번호의 쿠폰을 찾을 수 없습니다"));

        Member newMember = memberRepository.findByLoginId(loginId)
                .orElseThrow(()->new MemberNotFoundException("해당하는 로그인 아이디의 회원을 찾을 수 없습니다"));

        IssuedCoupon issuedCoupon =
                couponBoxRepository.save(IssuedCoupon.createIssuedCoupon(welcomeCoupon,newMember));

        return IssuedCouponResponse.changeEntityToDto(issuedCoupon);
    }

    public Page<IssuedCouponResponse> getIssuedCouponsByMemberId(Pageable pageable, Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberNotFoundException("해당하는 로그인 아이디의 회원을 찾을 수 없습니다"));

        CouponStatus couponStatus = couponStatusRepository.findByName("발급-삭제불가");

        // 이때 갖고와지는 쿠폰은, MemberId에 속해야하고 UseDateTime이 null 이어야 하며 (사용 안한 쿠폰), CouponStatus 이름이 '발급-삭제불가'가 아니어야함 (만료 안된 쿠폰)
        // 문제가 발생하는 부분 org.springframework.data.mapping.PropertyReferenceException: No property 'issue' found for type 'IssuedCoupon'
//        Page<IssuedCoupon> couponsOfMember = couponBoxRepository.findByMemberAndUseDateTimeIsNullAndCoupon_CouponStatus_NameNot(member,couponStatus,pageable);
//        return couponsOfMember.map(IssuedCouponResponse::changeEntityToDto);
        Page<IssuedCoupon> couponsOfMember = couponBoxRepository.getValidIssuedCoupon(memberId,couponStatus,pageable);

        return couponsOfMember.map(IssuedCouponResponse::changeEntityToDto);
    }

    public Page<IssuedCouponResponse> getIssuedCouponByLoginIdAndBookId(Pageable pageable, String loginId, String bookIsbn13){

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(()-> new MemberNotFoundException("해당하는 로그인 아이디의 회원을 찾을 수 없습니다"));

//        Page<IssuedCoupon> couponsOfMember = couponBoxRepository.findByMemberAndUseDateTimeIsNull(member,pageable);

//        return couponsOfMember.map(IssuedCouponResponse::changeEntityToDto);

        return null;
    }
}
