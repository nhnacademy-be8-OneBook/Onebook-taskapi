package com.nhnacademy.taskapi.coupon.domain.entity;

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

    @NotBlank
    @Column(nullable = false)
    private Integer minimumOrderAmount;

    @NotBlank
    @Column(nullable = false)
    private Integer discountAmount;
}
