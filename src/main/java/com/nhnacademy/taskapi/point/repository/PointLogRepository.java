package com.nhnacademy.taskapi.point.repository;

import com.nhnacademy.taskapi.point.domain.PointLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog, String> {
}