package com.nhnacademy.taskapi.points.service;

import com.nhnacademy.taskapi.points.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.points.request.PointPolicyRequest;
import com.nhnacademy.taskapi.points.response.PointPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointPolicyService {
    // 포인트 정책 생성
    PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest);

    // 포인트 정책 단건 조회
    PointPolicyResponse findPointPolicyById(Long pointPolicyId);

    // 포인트 정책 목록 조회
    Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable);

    // 포인트 정책 수정 (적립 금액)
    PointPolicyResponse updatePointPolicyById(Long pointPolicyId, PointPolicyRequest policyRequest);

    // 포인트 정책 삭제
    void deletePointPolicyById(Long pointPolicyId);
}
