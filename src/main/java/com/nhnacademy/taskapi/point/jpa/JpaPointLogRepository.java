package com.nhnacademy.taskapi.point.jpa;

import com.nhnacademy.taskapi.point.domain.PointLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPointLogRepository extends JpaRepository<PointLog, String> {
    Page<PointLog> findByPoint_Member_MemberIdOrderByPointLogUpdatedAtDesc(String member_id, Pageable pageable);
}