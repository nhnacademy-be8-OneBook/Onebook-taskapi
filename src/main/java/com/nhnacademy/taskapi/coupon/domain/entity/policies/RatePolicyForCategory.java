package com.nhnacademy.taskapi.coupon.domain.entity.policies;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.coupon.domain.entity.status.PolicyStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_policies_for_category")
@Getter
public class RatePolicyForCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePolicyForCategoryId;

    @Column(nullable = false)
    private Integer discountRate;

    private Integer minimumOrderAmount;

    private Integer maximumDiscountRate;

    @Column(nullable = false)
    private LocalDateTime expirationPeriodStart;

    @Column(nullable = false)
    private LocalDateTime expirationPeriodEnd;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 300)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "policy_status_id", nullable = false)
    private PolicyStatus policyStatus;
}
