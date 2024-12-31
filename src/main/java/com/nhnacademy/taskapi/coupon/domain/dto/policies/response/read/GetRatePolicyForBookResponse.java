package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetRatePolicyForBookResponse {

    private Long id;
    private Integer discountRate;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private String bookName;
    private String policyStatusName;

    public static GetRatePolicyForBookResponse changeEntityToDto(RatePolicyForBook ratePolicyForBook){
        return new GetRatePolicyForBookResponse(
                ratePolicyForBook.getRatePolicyForBookId(),
                ratePolicyForBook.getDiscountRate(),
                ratePolicyForBook.getMinimumOrderAmount(),
                ratePolicyForBook.getMaximumDiscountPrice(),
                ratePolicyForBook.getExpirationPeriodStart(),
                ratePolicyForBook.getExpirationPeriodEnd(),
                ratePolicyForBook.getName(),
                ratePolicyForBook.getDescription(),
                ratePolicyForBook.getBook().getTitle(),
                ratePolicyForBook.getPolicyStatus().getName()
        );
    }

}
