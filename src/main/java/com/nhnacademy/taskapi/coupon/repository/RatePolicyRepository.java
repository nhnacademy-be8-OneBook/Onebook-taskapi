package com.nhnacademy.taskapi.coupon.repository;

import com.nhnacademy.taskapi.coupon.domain.entity.RatePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePolicyRepository extends JpaRepository<RatePolicy, Long> {
}
