package com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AddPricePolicyForCategoryRequest {

    @NotNull
    private Integer minimumOrderAmount;
    @NotNull
    private Integer discountPrice;
    @NotNull
    private LocalDateTime expirationPeriodStart;
    @NotNull
    private LocalDateTime expirationPeriodEnd;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer policyStatusId;
}
