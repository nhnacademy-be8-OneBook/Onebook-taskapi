package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "rate_policies")
public class RatePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePolicyId;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer minimumOrderAmount;


    @Column(nullable = false)
    private Integer discountRate;


    @Column(nullable = false)
    private Integer maximumDiscountAmount;

    private RatePolicy(String name, Integer minimumOrderAmount, Integer discountRate, Integer maximumDiscountAmount) {
        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountRate = discountRate;
        this.maximumDiscountAmount = maximumDiscountAmount;
    }

    public static RatePolicy createRatePolicy(CreateRatePolicyRequest createRatePolicyRequest){

        return new RatePolicy(
               createRatePolicyRequest.getName(),
                createRatePolicyRequest.getMinimumOrderAmount(),
                createRatePolicyRequest.getDiscountRate(),
                createRatePolicyRequest.getMaximumDiscountAmount()
        );
    }
}
