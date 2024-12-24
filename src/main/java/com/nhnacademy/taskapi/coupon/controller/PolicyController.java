package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyResponse;
import com.nhnacademy.taskapi.coupon.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/policy/price-policy")
    public ResponseEntity<CreatePricePolicyResponse> createPricePolicy (@RequestBody CreatePricePolicyRequest createPricePolicyRequest){

        CreatePricePolicyResponse createPricePolicyResponse = policyService.createPricePolicy(createPricePolicyRequest);

        return ResponseEntity.ok(createPricePolicyResponse);
    }

    @PostMapping("/policy/rate-policy")
    public ResponseEntity<CreateRatePolicyResponse> createRatePolicy (@RequestBody CreateRatePolicyRequest createRatePolicyRequest){

        CreateRatePolicyResponse createRatePolicyResponse = policyService.createRatePolicy(createRatePolicyRequest);

        return ResponseEntity.ok(createRatePolicyResponse);
    }
}
