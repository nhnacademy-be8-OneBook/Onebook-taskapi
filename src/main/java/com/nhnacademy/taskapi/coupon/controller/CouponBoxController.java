package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.IssueCouponToMemberRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.IssuedCouponResponse;
import com.nhnacademy.taskapi.coupon.service.coupons.CouponBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class CouponBoxController {

    private final CouponBoxService couponBoxService;

    @PostMapping("/coupon/issue")
    public ResponseEntity<IssuedCouponResponse> issueCouponToMember(@RequestBody IssueCouponToMemberRequest issueCouponToMemberRequest){

        IssuedCouponResponse issuedCouponResponse = couponBoxService.issueCouponToMember(issueCouponToMemberRequest);

        return ResponseEntity.ok(issuedCouponResponse);
    }
}
