package com.nhnacademy.taskapi.point.jpa;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPointPolicyRepository extends JpaRepository<PointPolicy, String> {
    PointPolicy findByPointPolicyName(String pointPolicyName);
    Page<PointPolicy> findAllBy(Pageable pageable);
    Page<PointPolicy> findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(Pageable pageable);
}
