package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "price_policies")
public class PricePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pricePolicyId;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer minimumOrderAmount;

    @Column(nullable = false)
    private Integer discountAmount;

    private PricePolicy(String name, Integer minimumOrderAmount, Integer discountAmount){
        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountAmount = discountAmount;
    }

    public static PricePolicy createPricePolicy(CreatePricePolicyRequest createPricePolicyRequest){

        return new PricePolicy(
                createPricePolicyRequest.getName(),
                createPricePolicyRequest.getMinimumOrderAmount(),
                createPricePolicyRequest.getDiscountAmount()
        );
    }

}
