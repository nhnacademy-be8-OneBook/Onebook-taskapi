package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.service.policies.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/policies/rate/book")
    public ResponseEntity<AddRatePolicyForBookResponse> addRatePolicyForBook(@RequestBody AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        AddRatePolicyForBookResponse  addRatePolicyForBookResponse =
                policyService.addRatePolicyForBook(addRatePolicyForBookRequest);

        return ResponseEntity.ok(addRatePolicyForBookResponse);

    }

    @PostMapping("/policies/rate/category")
    public ResponseEntity<AddRatePolicyForCategoryResponse> addRatePolicyForCategory(@RequestBody AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){

        AddRatePolicyForCategoryResponse addRatePolicyForCategoryResponse =
                policyService.addRatePolicyForCategory(addRatePolicyForCategoryRequest);

        return ResponseEntity.ok(addRatePolicyForCategoryResponse);

    }



}
