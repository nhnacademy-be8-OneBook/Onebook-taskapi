package com.nhnacademy.taskapi.coupon.domain.entity.policies;

import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.entity.status.PolicyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_policies_for_category")
@Getter
@NoArgsConstructor
public class PricePolicyForCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pricePolicyForCategoryId;

    private Integer minimumOrderAmount;

    @Column(nullable = false)
    private Integer discountPrice;

    @Column(nullable = false)
    private LocalDateTime expirationPeriodStart;

    @Column(nullable = false)
    private LocalDateTime expirationPeriodEnd;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 300)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "policy_status_id",nullable = false)
    private PolicyStatus policyStatus;

    public PricePolicyForCategory(Integer minimumOrderAmount, Integer discountPrice, LocalDateTime expirationPeriodStart,
                                  LocalDateTime expirationPeriodEnd, String name, String description, Category category,
                                  PolicyStatus policyStatus) {
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountPrice = discountPrice;
        this.expirationPeriodStart = expirationPeriodStart;
        this.expirationPeriodEnd = expirationPeriodEnd;
        this.name = name;
        this.description = description;
        this.category = category;
        this.policyStatus = policyStatus;
    }

    public static PricePolicyForCategory createPricePolicyForCategory(AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest,
                                                                      Category category, PolicyStatus policyStatus){

        return new PricePolicyForCategory(
                addPricePolicyForCategoryRequest.getMinimumOrderAmount(),
                addPricePolicyForCategoryRequest.getDiscountPrice(),
                addPricePolicyForCategoryRequest.getExpirationPeriodStart(),
                addPricePolicyForCategoryRequest.getExpirationPeriodEnd(),
                addPricePolicyForCategoryRequest.getName(),
                addPricePolicyForCategoryRequest.getDescription(),
                category,
                policyStatus
        );

    }
}
