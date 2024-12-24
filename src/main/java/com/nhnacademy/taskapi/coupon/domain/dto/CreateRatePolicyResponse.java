package com.nhnacademy.taskapi.coupon.domain.dto;

import com.nhnacademy.taskapi.coupon.domain.entity.RatePolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateRatePolicyResponse {

    private String name;
    private Integer minimumOrderAmount;
    private Integer discountRate;
    private Integer maximumDiscountAmount;

    public static CreateRatePolicyResponse changeEntityToDto(RatePolicy ratePolicy) {

        return new CreateRatePolicyResponse(
                ratePolicy.getName(),
                ratePolicy.getMinimumOrderAmount(),
                ratePolicy.getDiscountRate(),
                ratePolicy.getMaximumDiscountAmount()
        );
    }
}
