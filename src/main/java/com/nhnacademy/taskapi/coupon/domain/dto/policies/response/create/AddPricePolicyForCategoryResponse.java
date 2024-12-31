package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create;

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

    private Integer minimumOrderAmount;
    private Integer discountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private Integer categoryId;
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
