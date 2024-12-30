package com.nhnacademy.taskapi.coupon.repository.policies;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePoliciesForBookRepository extends JpaRepository<RatePolicyForBook, Long> {
}
