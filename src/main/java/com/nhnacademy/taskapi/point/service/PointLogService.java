package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.response.PointLogResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface PointLogService {
    Page<PointLogResponse> findAllPointLogsByMemberId(Long member_id, Pageable pageable);
}
