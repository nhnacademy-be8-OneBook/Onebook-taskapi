package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long> {
    PointPolicy findByPointPolicyName(String pointPolicyName);
    List<PointPolicy> findAllByPoint(Point point);
}