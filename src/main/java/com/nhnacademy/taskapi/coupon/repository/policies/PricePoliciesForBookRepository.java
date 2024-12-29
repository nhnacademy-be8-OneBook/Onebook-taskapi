package com.nhnacademy.taskapi.coupon.repository.policies;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.PricePolicyForBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePoliciesForBookRepository extends JpaRepository<PricePolicyForBook, Long> {
}
