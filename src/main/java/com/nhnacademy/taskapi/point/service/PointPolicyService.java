package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.dto.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.dto.PointPolicyRequest;
import com.nhnacademy.taskapi.point.dto.PointPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PointPolicyService {
    // 포인트 정책 생성
    PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest);

    // 포인트 정책 단건 조회
    PointPolicyResponse findPointPolicyById(Long pointPolicyId);

    // 포인트 정책 목록 조회
    Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable);

    // 포인트 정책 수정
    PointPolicyResponse updatePointPolicyById(Long pointPolicyId, PointPolicyRequest policyRequest);

    // 포인트 정책 활성화 상태 변경
    void updatePointPolicyState(Long pointPolicyId, boolean isActive);

    // 포인트 정책 비활성화 처리
    void deactivatePointPolicy(Long pointPolicyId);

    @Transactional
    void activatePointPolicy(Long pointPolicyId);
}
