package com.nhnacademy.taskapi.coupon.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateRatePolicyRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    private Integer minimumOrderAmount;

    @NotNull
    private Integer discountRate;

    @NotNull
    private Integer maximumDiscountAmount;

}