package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read;

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
public class GetRatePolicyForCategoryResponse {

    private Long id;
    private Integer discountRate;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String categoryName;
    private String policyStatusName;

    public static GetRatePolicyForCategoryResponse changeEntityToDto(RatePolicyForCategory ratePolicyForCategory){
        return new GetRatePolicyForCategoryResponse(
                ratePolicyForCategory.getRatePolicyForCategoryId(),
                ratePolicyForCategory.getDiscountRate(),
                ratePolicyForCategory.getMinimumOrderAmount(),
                ratePolicyForCategory.getMaximumDiscountRate(),
                ratePolicyForCategory.getExpirationPeriodStart(),
                ratePolicyForCategory.getExpirationPeriodEnd(),
                ratePolicyForCategory.getName(),
                ratePolicyForCategory.getDescription(),
                ratePolicyForCategory.getCategory().getName(),
                ratePolicyForCategory.getPolicyStatus().getName()
        );
    }
}
