package com.nhnacademy.taskapi.points.service;

import com.nhnacademy.taskapi.points.response.PointLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointLogService {
    Page<PointLogResponse> findAllPointLogsByMemberId(Long member_id, Pageable pageable);
}
