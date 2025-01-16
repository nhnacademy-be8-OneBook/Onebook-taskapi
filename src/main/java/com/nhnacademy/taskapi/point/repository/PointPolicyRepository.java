package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long> {
    // 기본적인 CRUD 메서드는 JpaRepository에서 제공되므로 추가로 작성할 필요 없음
    @Query("SELECT pp FROM PointPolicy pp WHERE pp.pointPolicyState = true ORDER BY pp.pointPolicyCreatedAt DESC")
    List<PointPolicy> findActivePointPolicies();
}


//활성화된 포인트 정책만 조회할 수 있도록