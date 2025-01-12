package com.nhnacademy.taskapi.coupon.service.coupons;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.CreateCouponRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.CouponResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.coupons.Coupon;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.CouponStatus;
import com.nhnacademy.taskapi.coupon.exception.CouponCannotDeleteException;
import com.nhnacademy.taskapi.coupon.exception.CouponNotFoundException;
import com.nhnacademy.taskapi.coupon.exception.PolicyNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.coupons.CouponRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.PricePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.status.CouponStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<CouponResponse> couponCreateResponses = new ArrayList<>();
        LocalDateTime creationTime = LocalDateTime.now();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createRateCouponForBook(ratePolicyForBook, unUsedStatus,creationTime));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponCreateResponses.add(couponResponse);
        }

        return couponCreateResponses;

    }

    public List<CouponResponse> CreateRateCouponForCategory(CreateCouponRequest createCouponRequest){

        RatePolicyForCategory ratePolicyForCategory = ratePoliciesForCategoryRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponCreateResponses = new ArrayList<>();
        LocalDateTime creationTime = LocalDateTime.now();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createRateCouponForCategory(ratePolicyForCategory, unUsedStatus,creationTime));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponCreateResponses.add(couponResponse);
        }

        return couponCreateResponses;
    }

    public List<CouponResponse> CreatePriceCouponForBook(CreateCouponRequest createCouponRequest){

        PricePolicyForBook pricePolicyForBook = pricePoliciesForBookRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponCreateResponses = new ArrayList<>();
        LocalDateTime creationTime = LocalDateTime.now();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createPriceCouponForBook(pricePolicyForBook, unUsedStatus,creationTime));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponCreateResponses.add(couponResponse);
        }

        return couponCreateResponses;
    }

    public List<CouponResponse> CreatePriceCouponForCategory(CreateCouponRequest createCouponRequest){

        PricePolicyForCategory pricePolicyForCategory = pricePoliciesForCategoryRepository.findById(createCouponRequest.getPolicyId())
                .orElseThrow(()-> new PolicyNotFoundException("해당하는 ID의 정책을 찾을 수 없습니다"));

        CouponStatus unUsedStatus = couponStatusRepository.findByName("미발급");
        List<CouponResponse> couponCreateResponses = new ArrayList<>();
        LocalDateTime creationTime = LocalDateTime.now();

        for(int i = 0; i < createCouponRequest.getCount(); i++){
            Coupon coupon = couponRepository.save(Coupon.createPriceCouponForCategory(pricePolicyForCategory, unUsedStatus,creationTime));
            CouponResponse couponResponse = CouponResponse.changeEntityToDto(coupon);
            couponCreateResponses.add(couponResponse);
        }

        return couponCreateResponses;
    }

    public Page<CouponResponse> getAllCoupons(Pageable pageable){

        Page<Coupon> coupons = couponRepository.findAll(pageable);
        return coupons.map(CouponResponse::changeEntityToDto);
    }

    public CouponResponse deleteCoupon(String couponNumber){
        Coupon coupon = couponRepository.findByCouponNumber(couponNumber).
                orElseThrow(()->new CouponNotFoundException("해당하는 쿠폰넘버의 쿠폰이 존재하지 않습니다"));

        if(coupon.getCouponStatus().getName().equals("발급-삭제불가")){
            throw new CouponCannotDeleteException("해당 쿠폰은 이미 발급되고 사용되어 삭제할 수 없습니다");
        }

        couponRepository.delete(coupon);
        return CouponResponse.changeEntityToDto(coupon);
    }
}
