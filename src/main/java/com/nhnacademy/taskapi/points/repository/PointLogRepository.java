package com.nhnacademy.taskapi.points.repository;

import com.nhnacademy.taskapi.points.domain.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
}