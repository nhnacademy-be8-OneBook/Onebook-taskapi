package com.nhnacademy.taskapi.coupon.domain.entity;

import com.nhnacademy.taskapi.book.domain.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "coupons_mapping_books")
public class CouponMappingBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponMappingBookId;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
