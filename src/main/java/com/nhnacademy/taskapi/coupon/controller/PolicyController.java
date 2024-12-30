package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.service.policies.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/policies/price/book")
    public ResponseEntity<AddPricePolicyForBookResponse> addPricePolicyForBook
            (@RequestBody AddPricePolicyForBookRequest addPricePolicyForBookRequest){

        AddPricePolicyForBookResponse addPricePolicyForBookResponse =
                policyService.addPricePolicyForBook(addPricePolicyForBookRequest);

        return ResponseEntity.ok(addPricePolicyForBookResponse);
    }

    @PostMapping("/policies/price/category")
    public ResponseEntity<AddPricePolicyForCategoryResponse> addPricePolicyForCategory
            (@RequestBody AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){

        AddPricePolicyForCategoryResponse addPricePolicyForCategoryResponse =
                policyService.addPricePolicyForCategory(addPricePolicyForCategoryRequest);

        return ResponseEntity.ok(addPricePolicyForCategoryResponse);
    }

    @GetMapping("/policies/rate/book")
    public ResponseEntity<List<GetRatePolicyForBookResponse>> getRatePoliciesForBook
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<GetRatePolicyForBookResponse> response = policyService.getRatePoliciesForBook(pageNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policies/rate/category")
    public ResponseEntity<List<GetRatePolicyForCategoryResponse>> getRatePoliciesForCategory
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<GetRatePolicyForCategoryResponse> responses = policyService.getRatePoliciesForCategory(pageNo);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/policies/price/book")
    public ResponseEntity<List<GetPricePolicyForBookResponse>> getPricePoliciesForBook
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<GetPricePolicyForBookResponse> responses = policyService.getPricePoliciesForBook(pageNo);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/policies/price/category")
    public ResponseEntity<List<GetPricePolicyForCategoryResponse>> getPricePoliciesForCategory
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<GetPricePolicyForCategoryResponse> responses = policyService.getPricePoliciesForCategory(pageNo);
        return ResponseEntity.ok(responses);
    }

}
