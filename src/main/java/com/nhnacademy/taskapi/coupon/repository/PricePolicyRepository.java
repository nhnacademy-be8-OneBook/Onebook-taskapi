package com.nhnacademy.taskapi.coupon.repository;

import com.nhnacademy.taskapi.coupon.domain.entity.PricePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePolicyRepository extends JpaRepository<PricePolicy, Long> {
}
