package com.nhnacademy.taskapi.coupon.domain.entity.policies;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.coupon.domain.entity.status.PolicyStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_policies_for_book")
@Getter
public class RatePolicyForBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePolicyForBookId;

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
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "policyStatusId", nullable = false)
    private PolicyStatus policyStatus;
}
