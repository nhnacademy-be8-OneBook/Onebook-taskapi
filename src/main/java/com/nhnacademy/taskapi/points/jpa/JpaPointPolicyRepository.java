package com.nhnacademy.taskapi.points.jpa;

import com.nhnacademy.taskapi.points.domain.PointPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPointPolicyRepository extends JpaRepository<PointPolicy, Long> {
    PointPolicy findByPointPolicyName(String pointPolicyName);
    Page<PointPolicy> findAllBy(Pageable pageable);
    Page<PointPolicy> findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(Pageable pageable);
}