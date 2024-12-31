package com.nhnacademy.taskapi.point.service;

import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointPolicyService {
    // 포인트 정책 생성
    PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest);

    // 포인트 정책 단건 조회
    PointPolicyResponse findPointPolicyById(String pointPolicyId);

    // 포인트 정책 목록 조회
    Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable);

    // 포인트 정책 수정
    PointPolicyResponse updatePointPolicyById(String pointPolicyId, PointPolicyRequest policyRequest);

    // 포인트 정책 삭제
    void deletePointPolicyById(String pointPolicyId);
}
