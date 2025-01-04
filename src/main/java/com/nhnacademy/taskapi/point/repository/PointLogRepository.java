package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    Page<PointLog> findByPoint_Member_IdOrderByPointLogUpdatedAtDesc(Long memberId, Pageable pageable);
}
