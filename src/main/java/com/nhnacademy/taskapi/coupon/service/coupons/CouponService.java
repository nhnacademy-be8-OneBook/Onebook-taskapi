package com.nhnacademy.taskapi.coupon.service.coupons;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.CreateCouponRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.CouponResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.Coupon;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import com.nhnacademy.taskapi.coupon.exception.PolicyNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.status.CouponStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponStatusRepository couponStatusRepository;
    private final RatePoliciesForBookRepository ratePoliciesForBookRepository;
    private final RatePoliciesForCategoryRepository ratePoliciesForCategoryRepository;
    private final PricePoliciesForBookRepository pricePoliciesForBookRepository;
    private final PricePoliciesForCategoryRepository pricePoliciesForCategoryRepository;

    public List<CouponResponse> CreateRateCouponForBook(CreateCouponRequest createCouponRequest){

        RatePolicyForBook ratePolicyForBook = ratePoliciesForBookRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponResponses = new ArrayList<>();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createRateCouponForBook(ratePolicyForBook, unUsedStatus));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponResponses.add(couponResponse);
        }

        return couponResponses;

    }

    public List<CouponResponse> CreateRateCouponForCategory(CreateCouponRequest createCouponRequest){

        RatePolicyForCategory ratePolicyForCategory = ratePoliciesForCategoryRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponResponses = new ArrayList<>();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createRateCouponForCategory(ratePolicyForCategory, unUsedStatus));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponResponses.add(couponResponse);
        }

        return couponResponses;
    }

    public List<CouponResponse> CreatePriceCouponForBook(CreateCouponRequest createCouponRequest){

        PricePolicyForBook pricePolicyForBook = pricePoliciesForBookRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponResponses = new ArrayList<>();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createPriceCouponForBook(pricePolicyForBook, unUsedStatus));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponResponses.add(couponResponse);
        }

        return couponResponses;
    }

    public List<CouponResponse> CreatePriceCouponForCategory(CreateCouponRequest createCouponRequest){

        PricePolicyForCategory pricePolicyForCategory = pricePoliciesForCategoryRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponResponses = new ArrayList<>();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createPriceCouponForCategory(pricePolicyForCategory, unUsedStatus));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponResponses.add(couponResponse);
        }

        return couponResponses;
    }


}
