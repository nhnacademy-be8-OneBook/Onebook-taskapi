package com.nhnacademy.taskapi.coupon.repository.policies;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePoliciesForCategoryRepository extends JpaRepository<PricePolicyForCategory, Long> {
}
