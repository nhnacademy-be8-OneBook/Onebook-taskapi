package com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
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
public class AddPricePolicyForBookResponse {

    private Integer minimumOrderAmount;
    private Integer discountPrice;
    private LocalDateTime expirationPeriodStart;
    private LocalDateTime expirationPeriodEnd;
    private String name;
    private String description;
    private Long bookId;
    private Integer policyStatusId;

    public static AddPricePolicyForBookResponse changeEntityToDto(PricePolicyForBook pricePolicyForBook){
        return new AddPricePolicyForBookResponse(
                pricePolicyForBook.getMinimumOrderAmount(),
                pricePolicyForBook.getDiscountPrice(),
                pricePolicyForBook.getExpirationPeriodStart(),
                pricePolicyForBook.getExpirationPeriodEnd(),
                pricePolicyForBook.getName(),
                pricePolicyForBook.getDescription(),
                pricePolicyForBook.getBook().getBookId(),
                pricePolicyForBook.getPolicyStatus().getPolicyStatusId()
        );
    }
}
