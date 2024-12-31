package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetPricePolicyForCategoryResponse {

    private Long id;
    private Integer minimumOrderAmount;
    private Integer discountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String categoryName;
    private String policyStatusName;

    public static GetPricePolicyForCategoryResponse changeEntityToDto(PricePolicyForCategory pricePolicyForCategory){
        return new GetPricePolicyForCategoryResponse(
                pricePolicyForCategory.getPricePolicyForCategoryId(),
                pricePolicyForCategory.getMinimumOrderAmount(),
                pricePolicyForCategory.getDiscountPrice(),
                pricePolicyForCategory.getExpirationPeriodStart(),
                pricePolicyForCategory.getExpirationPeriodEnd(),
                pricePolicyForCategory.getName(),
                pricePolicyForCategory.getDescription(),
                pricePolicyForCategory.getCategory().getName(),
                pricePolicyForCategory.getPolicyStatus().getName()
        );
    }
}
