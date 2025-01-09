package com.nhnacademy.taskapi.coupon.domain.dto.policies.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UpdateRatePolicyForBookRequest {

    private Long id;
    @NotNull
    private Integer discountRate;
    @NotNull
    private Integer minimumOrderAmount;
    @NotNull
    private Integer maximumDiscountPrice;
    @NotNull
    private LocalDateTime expirationPeriodStart;
    @NotNull
    private LocalDateTime expirationPeriodEnd;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String bookIsbn13;

}
