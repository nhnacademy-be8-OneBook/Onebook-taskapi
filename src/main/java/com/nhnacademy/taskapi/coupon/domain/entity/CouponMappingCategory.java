package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.category.domain.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CouponMappingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "id")
    private Category category;
}
