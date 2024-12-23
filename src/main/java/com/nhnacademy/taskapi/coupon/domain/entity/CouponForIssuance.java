package com.nhnacademy.taskapi.coupon.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class CouponForIssuance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id",nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private boolean usage;

    @Column(nullable = false)
    private LocalDateTime issueDateTime;

    private LocalDateTime useDateTime;
}
