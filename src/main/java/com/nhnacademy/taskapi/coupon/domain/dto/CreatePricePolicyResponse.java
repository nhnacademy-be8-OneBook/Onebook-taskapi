package com.nhnacademy.taskapi.coupon.domain.dto;

import com.nhnacademy.taskapi.coupon.domain.entity.PricePolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePricePolicyResponse {

    private String name;
    private Integer minimumOrderAmount;
    private Integer discountAmount;

    public static CreatePricePolicyResponse changeEntityToDto(PricePolicy pricePolicy) {

       return new CreatePricePolicyResponse(
               pricePolicy.getName(),
               pricePolicy.getMinimumOrderAmount(),
               pricePolicy.getDiscountAmount()
       );
    }
}
