package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create;

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
public class AddRatePolicyForBookResponse {

    private Integer discountRate;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private Long bookId;
    private Integer policyStatusId;

    public static AddRatePolicyForBookResponse changeEntityToDto(RatePolicyForBook ratePolicyForBook) {

        return new AddRatePolicyForBookResponse(
               ratePolicyForBook.getDiscountRate(),
               ratePolicyForBook.getMinimumOrderAmount(),
               ratePolicyForBook.getMaximumDiscountRate(),
               ratePolicyForBook.getExpirationPeriodStart(),
               ratePolicyForBook.getExpirationPeriodEnd(),
               ratePolicyForBook.getName(),
               ratePolicyForBook.getDescription(),
               ratePolicyForBook.getBook().getBookId(),
               ratePolicyForBook.getPolicyStatus().getPolicyStatusId()
        );
    }
}
