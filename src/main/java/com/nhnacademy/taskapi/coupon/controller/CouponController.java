package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.CreateCouponRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.CouponResponse;
import com.nhnacademy.taskapi.coupon.service.coupons.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/rate/book")
    public ResponseEntity<List<CouponResponse>> CreateRateCouponForBook(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreateRateCouponForBook(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/coupon/rate/category")
    public ResponseEntity<List<CouponResponse>> CreateRateCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponse = couponService.CreateRateCouponForCategory(createCouponRequest);
        return ResponseEntity.ok(couponResponse);
    }

    @PostMapping("/coupon/price/book")
    public ResponseEntity<List<CouponResponse>> CreatePriceCouponForBook(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreatePriceCouponForBook(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/coupon/price/category")
    public ResponseEntity<List<CouponResponse>> CreatePriceCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreatePriceCouponForCategory(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }
}
