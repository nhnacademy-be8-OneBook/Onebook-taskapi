package com.nhnacademy.taskapi.coupon.domain.dto.policies.response;

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
    private Long bookId;
    @NotNull
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
