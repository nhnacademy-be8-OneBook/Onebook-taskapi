package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.PointLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    Page<PointLog> findByPoint_Member_Id(Long memberId, Pageable pageable);
}
