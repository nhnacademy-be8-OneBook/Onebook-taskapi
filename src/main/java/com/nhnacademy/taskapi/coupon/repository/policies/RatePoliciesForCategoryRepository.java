package com.nhnacademy.taskapi.coupon.repository.policies;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePoliciesForCategoryRepository extends JpaRepository<RatePolicyForCategory, Long> {
}
