package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.category.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_categories")
public class CouponMappingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingCategoryId;

    @OneToOne
    @JoinColumn(name = "couponId" ,nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
}
