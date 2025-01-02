package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.PricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.PricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.RatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.RatePolicyForCategoryResponse;
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

    // Create
    @PostMapping("/policies/rate/book")
    public ResponseEntity<RatePolicyForBookResponse> addRatePolicyForBook(@RequestBody AddRatePolicyForBookRequest addRatePolicyForBookRequest){

        RatePolicyForBookResponse  addRatePolicyForBookResponse =
                policyService.addRatePolicyForBook(addRatePolicyForBookRequest);
        return ResponseEntity.ok(addRatePolicyForBookResponse);
    }

    @PostMapping("/policies/rate/category")
    public ResponseEntity<RatePolicyForCategoryResponse> addRatePolicyForCategory(@RequestBody AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest){

        RatePolicyForCategoryResponse addRatePolicyForCategoryResponse =
                policyService.addRatePolicyForCategory(addRatePolicyForCategoryRequest);
        return ResponseEntity.ok(addRatePolicyForCategoryResponse);
    }

    @PostMapping("/policies/price/book")
    public ResponseEntity<PricePolicyForBookResponse> addPricePolicyForBook
            (@RequestBody AddPricePolicyForBookRequest addPricePolicyForBookRequest){

        PricePolicyForBookResponse addPricePolicyForBookResponse =
                policyService.addPricePolicyForBook(addPricePolicyForBookRequest);
        return ResponseEntity.ok(addPricePolicyForBookResponse);
    }

    @PostMapping("/policies/price/category")
    public ResponseEntity<PricePolicyForCategoryResponse> addPricePolicyForCategory
            (@RequestBody AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest){

        PricePolicyForCategoryResponse addPricePolicyForCategoryResponse =
                policyService.addPricePolicyForCategory(addPricePolicyForCategoryRequest);
        return ResponseEntity.ok(addPricePolicyForCategoryResponse);
    }


    // Read All
    @GetMapping("/policies/rate/book")
    public ResponseEntity<List<RatePolicyForBookResponse>> getRatePoliciesForBook
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<RatePolicyForBookResponse> response = policyService.getRatePoliciesForBook(pageNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policies/rate/category")
    public ResponseEntity<List<RatePolicyForCategoryResponse>> getRatePoliciesForCategory
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<RatePolicyForCategoryResponse> responses = policyService.getRatePoliciesForCategory(pageNo);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/policies/price/book")
    public ResponseEntity<List<PricePolicyForBookResponse>> getPricePoliciesForBook
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<PricePolicyForBookResponse> responses = policyService.getPricePoliciesForBook(pageNo);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/policies/price/category")
    public ResponseEntity<List<PricePolicyForCategoryResponse>> getPricePoliciesForCategory
            (@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo)
    {
        List<PricePolicyForCategoryResponse> responses = policyService.getPricePoliciesForCategory(pageNo);
        return ResponseEntity.ok(responses);
    }

    // Read One
    @GetMapping("/policies/rate/book/{id}")
    public ResponseEntity<RatePolicyForBookResponse> getRatePolicyForBook(@PathVariable Long id)
    {
        RatePolicyForBookResponse response = policyService.getRatePolicyForBook(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policies/rate/category/{id}")
    public ResponseEntity<RatePolicyForCategoryResponse> getRatePolicyForCategory(@PathVariable Long id)
    {
        RatePolicyForCategoryResponse response = policyService.getRatePolicyForCategory(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policies/price/book/{id}")
    public ResponseEntity<PricePolicyForBookResponse> getPricePolicyForBook(@PathVariable Long id)
    {
        PricePolicyForBookResponse response = policyService.getPricePolicyForBook(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/policies/price/category/{id}")
    public ResponseEntity<PricePolicyForCategoryResponse> getPricePolicyForCategory(@PathVariable Long id)
    {
        PricePolicyForCategoryResponse response = policyService.getPricePolicyForCategory(id);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/policies/rate/book/{id}")
    public ResponseEntity<RatePolicyForBookResponse> deleteRatePolicyForBook(@PathVariable Long id)
    {
        RatePolicyForBookResponse response = policyService.deleteRatePolicyForBook(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/policies/rate/category/{id}")
    public ResponseEntity<RatePolicyForCategoryResponse> deleteRatePolicyForCategory(@PathVariable Long id)
    {
        RatePolicyForCategoryResponse response = policyService.deleteRatePolicyForCategory(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/policies/price/book/{id}")
    public ResponseEntity<PricePolicyForBookResponse> deletePricePolicyForBook(@PathVariable Long id)
    {
        PricePolicyForBookResponse response = policyService.deletePricePolicyForBook(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/policies/price/category/{id}")
    public ResponseEntity<PricePolicyForCategoryResponse> deletePricePolicyForCategory(@PathVariable Long id)
    {
        PricePolicyForCategoryResponse response = policyService.deletePricePolicyForCategory(id);
        return ResponseEntity.ok(response);
    }

}
