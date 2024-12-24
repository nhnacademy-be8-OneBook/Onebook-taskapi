package com.nhnacademy.taskapi.coupon.service;

import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.PricePolicy;
import com.nhnacademy.taskapi.coupon.domain.entity.RatePolicy;
import com.nhnacademy.taskapi.coupon.repository.PricePolicyRepository;
import com.nhnacademy.taskapi.coupon.repository.RatePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService{

    private final RatePolicyRepository ratePolicyRepository;
    private final PricePolicyRepository pricePolicyRepository;

    public CreatePricePolicyResponse createPricePolicy(CreatePricePolicyRequest createPricePolicyRequest){

        PricePolicy pricePolicy = pricePolicyRepository.save(PricePolicy.createPricePolicy(createPricePolicyRequest));

        return CreatePricePolicyResponse.changeEntityToDto(pricePolicy);
    }

    public CreateRatePolicyResponse createRatePolicy(CreateRatePolicyRequest createRatePolicyRequest){

        RatePolicy ratePolicy = ratePolicyRepository.save(RatePolicy.createRatePolicy(createRatePolicyRequest));

        return CreateRatePolicyResponse.changeEntityToDto(ratePolicy);
    }

}
