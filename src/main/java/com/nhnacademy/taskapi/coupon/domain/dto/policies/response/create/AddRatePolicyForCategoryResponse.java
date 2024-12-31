package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AddRatePolicyForCategoryResponse {

    private Integer discountRate;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private Integer categoryId;
    private Integer policyStatusId;

    public static AddRatePolicyForCategoryResponse changeEntityToDto(RatePolicyForCategory ratePolicyForCategory) {

        return new AddRatePolicyForCategoryResponse(
                ratePolicyForCategory.getDiscountRate(),
                ratePolicyForCategory.getMinimumOrderAmount(),
                ratePolicyForCategory.getMaximumDiscountPrice(),
                ratePolicyForCategory.getExpirationPeriodStart(),
                ratePolicyForCategory.getExpirationPeriodEnd(),
                ratePolicyForCategory.getName(),
                ratePolicyForCategory.getDescription(),
                ratePolicyForCategory.getCategory().getCategoryId(),
                ratePolicyForCategory.getPolicyStatus().getPolicyStatusId()
        );
    }
}
