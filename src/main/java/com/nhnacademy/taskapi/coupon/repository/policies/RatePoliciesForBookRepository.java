package com.nhnacademy.taskapi.coupon.repository.policies;

import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePoliciesForBookRepository extends JpaRepository<RatePolicyForBook, Long> {
    Page<RatePolicyForBook> findByPolicyStatus_NameOrPolicyStatus_Name(String policyStatusName, String policyStatusName1, Pageable pageable);
}
