package com.nhnacademy.taskapi.coupon.domain.dto.policies.response;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AddPricePolicyForCategoryResponse {

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

    public static AddPricePolicyForCategoryResponse changeEntityToDto(PricePolicyForCategory pricePolicyForCategory){
        return new AddPricePolicyForCategoryResponse(
                pricePolicyForCategory.getMinimumOrderAmount(),
                pricePolicyForCategory.getDiscountPrice(),
                pricePolicyForCategory.getExpirationPeriodStart(),
                pricePolicyForCategory.getExpirationPeriodEnd(),
                pricePolicyForCategory.getName(),
                pricePolicyForCategory.getDescription(),
                pricePolicyForCategory.getCategory().getCategoryId(),
                pricePolicyForCategory.getPolicyStatus().getPolicyStatusId()
        );
    }
}
