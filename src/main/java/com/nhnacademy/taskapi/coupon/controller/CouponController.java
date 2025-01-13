package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.CreateCouponRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.CouponResponse;
import com.nhnacademy.taskapi.coupon.service.coupons.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/rate/book")
    public ResponseEntity<List<CouponResponse>> createRateCouponForBook(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreateRateCouponForBook(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/coupon/rate/category")
    public ResponseEntity<List<CouponResponse>> createRateCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreateRateCouponForCategory(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/coupon/price/book")
    public ResponseEntity<List<CouponResponse>> createPriceCouponForBook(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreatePriceCouponForBook(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/coupon/price/category")
    public ResponseEntity<List<CouponResponse>> createPriceCouponForCategory(@RequestBody CreateCouponRequest createCouponRequest){
        List<CouponResponse> couponResponses = couponService.CreatePriceCouponForCategory(createCouponRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @GetMapping("/coupon")
    public ResponseEntity<Page<CouponResponse>> getAllCoupons(Pageable pageable){

        Page<CouponResponse> couponResponses = couponService.getAllCoupons(pageable);
        return ResponseEntity.ok(couponResponses);
    }

    @DeleteMapping("/coupon/{coupon-number}")
    public ResponseEntity<CouponResponse> deleteCoupon(@PathVariable(name = "coupon-number") String couponNumber){

        CouponResponse couponResponse = couponService.deleteCoupon(couponNumber);
        return ResponseEntity.ok(couponResponse);
    }
}
