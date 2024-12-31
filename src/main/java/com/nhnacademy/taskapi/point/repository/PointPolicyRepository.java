package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, String> {
    // 기본적인 CRUD 메서드는 JpaRepository에서 제공되므로 추가로 작성할 필요 없음
    PointPolicy findByPointPolicyName(String pointPolicyName);
}
