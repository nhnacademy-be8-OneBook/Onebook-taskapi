package com.nhnacademy.taskapi.coupon.controller;

import com.nhnacademy.taskapi.coupon.domain.dto.coupons.request.IssueCouponToMemberRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.coupons.response.IssuedCouponResponse;
import com.nhnacademy.taskapi.coupon.service.coupons.CouponBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class CouponBoxController {

    private final CouponBoxService couponBoxService;

    @GetMapping("/coupon/coupon-box/{login-id}")
    public ResponseEntity<Page<IssuedCouponResponse>> getIssuedCouponsByLoginId(Pageable pageable, @PathVariable(name = "login-id")String loginId){
        Page<IssuedCouponResponse> couponsOfMembers = couponBoxService.getIssuedCouponsByLoginId(pageable,loginId);
        return ResponseEntity.ok(couponsOfMembers);
    }

    @PostMapping("/coupon/issue")
    public ResponseEntity<IssuedCouponResponse> issueCouponToMember(@RequestBody IssueCouponToMemberRequest issueCouponToMemberRequest){

        IssuedCouponResponse issuedCouponResponse = couponBoxService.issueCouponToMember(issueCouponToMemberRequest);
        return ResponseEntity.ok(issuedCouponResponse);
    }

    @PostMapping("/coupon/issue/welcome/{login-id}")
    public ResponseEntity<IssuedCouponResponse> issueWelcomeCouponToMember(@PathVariable(name = "login-id") String loginId){
        IssuedCouponResponse issuedWelcomeCouponResponse =
                couponBoxService.issueWelcomeCouponToMember(loginId);

        return ResponseEntity.ok(issuedWelcomeCouponResponse);
    }
}
