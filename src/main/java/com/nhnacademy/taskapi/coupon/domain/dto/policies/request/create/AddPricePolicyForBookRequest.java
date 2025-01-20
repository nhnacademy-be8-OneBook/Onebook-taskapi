package com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AddPricePolicyForBookRequest {

    @NotNull(message = "최소주문금액은 비어있을 수 없습니다")
    private Integer minimumOrderAmount;
    @NotNull(message = "할인금액은 비어있을 수 없습니다")
    private Integer discountPrice;
    @NotNull(message = "유효기간 start, 비어있을 수 없습니다")
    private LocalDateTime expirationPeriodStart;
    @NotNull(message = "유효기간 end, 비어있을 수 없습니다")
    private LocalDateTime expirationPeriodEnd;
    @NotBlank(message = "정책의 이름이 비어있을 수 없습니다")
    @Size(max=100)
    private String name;
    @Size(max=200)
    private String description;
    @NotBlank(message = "대상 도서의 isbn13이 비어있을 수 없습니다")
    @Size(min=13, message = "isbn13 코드 13자리를 입력하셔야 합니다")
    private String bookIsbn13;

}
