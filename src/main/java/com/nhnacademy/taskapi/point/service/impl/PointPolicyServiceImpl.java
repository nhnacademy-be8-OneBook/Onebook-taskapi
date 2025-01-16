package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.dto.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.dto.PointPolicyRequest;
import com.nhnacademy.taskapi.point.dto.PointPolicyResponse;
import com.nhnacademy.taskapi.point.repository.PointPolicyRepository;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {

    private final PointPolicyRepository pointPolicyRepository;

    @Override
    public PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest) {
        // 포인트 정책 생성 로직
        PointPolicy pointPolicy = PointPolicy.builder()
                .pointPolicyName(policyRequest.getPointPolicyName())
                .pointPolicyRate(policyRequest.getPointPolicyRate())
                .pointPolicyConditionAmount(policyRequest.getPointPolicyConditionAmount())
                .pointPolicyCondition(policyRequest.getPointPolicyCondition())
                .pointPolicyApplyAmount(policyRequest.getPointPolicyApplyAmount())
                .pointPolicyApplyType(policyRequest.isPointPolicyApplyType())
                .pointPolicyState(true) // 기본값을 활성화 상태로 설정
                .build();

        pointPolicyRepository.save(pointPolicy);

        return PointPolicyResponse.builder()
                .pointPolicyId(pointPolicy.getPointPolicyId())
                .pointPolicyName(pointPolicy.getPointPolicyName())
                .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                .pointPolicyRate(pointPolicy.getPointPolicyRate())
                .pointPolicyApplyAmount(pointPolicy.getPointPolicyApplyAmount())
                .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                .pointPolicyUpdatedAt(pointPolicy.getPointPolicyUpdatedAt())
                .pointPolicyState(pointPolicy.isPointPolicyState())
                .build();
    }

    @Override
    public PointPolicyResponse findPointPolicyById(Long pointPolicyId) {
        // 포인트 정책 조회
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new IllegalArgumentException("PointPolicy not found"));

        return PointPolicyResponse.builder()
                .pointPolicyId(pointPolicy.getPointPolicyId())
                .pointPolicyName(pointPolicy.getPointPolicyName())
                .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                .pointPolicyRate(pointPolicy.getPointPolicyRate())
                .pointPolicyApplyAmount(pointPolicy.getPointPolicyApplyAmount())
                .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                .pointPolicyUpdatedAt(pointPolicy.getPointPolicyUpdatedAt())
                .pointPolicyState(pointPolicy.isPointPolicyState())
                .build();
    }

    @Override
    public Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable) {
        // 포인트 정책 목록 조회
        return pointPolicyRepository.findAll(pageable)
                .map(pointPolicy -> PointPolicyResponse.builder()
                        .pointPolicyId(pointPolicy.getPointPolicyId())
                        .pointPolicyName(pointPolicy.getPointPolicyName())
                        .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                        .pointPolicyRate(pointPolicy.getPointPolicyRate())
                        .pointPolicyApplyAmount(pointPolicy.getPointPolicyApplyAmount())
                        .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                        .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                        .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                        .pointPolicyUpdatedAt(pointPolicy.getPointPolicyUpdatedAt())
                        .pointPolicyState(pointPolicy.isPointPolicyState())
                        .build());
    }

    @Override
    public PointPolicyResponse updatePointPolicyById(Long pointPolicyId, PointPolicyRequest policyRequest) {
        // 포인트 정책 수정 로직
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new IllegalArgumentException("PointPolicy not found"));

        pointPolicy.updatePointPolicyName(policyRequest.getPointPolicyName());
        pointPolicy.updatePointPolicyConditionAmount(policyRequest.getPointPolicyConditionAmount());
        pointPolicy.updatePointPolicyApplyAmount(policyRequest.getPointPolicyApplyAmount());
        pointPolicy.updatePointPolicyRate(policyRequest.getPointPolicyRate());
        pointPolicy.updatePointPolicyCondition(policyRequest.getPointPolicyCondition());
        pointPolicy.updatePointPolicyApplyType(policyRequest.isPointPolicyApplyType());

        pointPolicyRepository.save(pointPolicy);

        return PointPolicyResponse.builder()
                .pointPolicyId(pointPolicy.getPointPolicyId())
                .pointPolicyName(pointPolicy.getPointPolicyName())
                .pointPolicyConditionAmount(pointPolicy.getPointPolicyConditionAmount())
                .pointPolicyRate(pointPolicy.getPointPolicyRate())
                .pointPolicyApplyAmount(pointPolicy.getPointPolicyApplyAmount())
                .pointPolicyCondition(pointPolicy.getPointPolicyCondition())
                .pointPolicyApplyType(pointPolicy.isPointPolicyApplyType())
                .pointPolicyCreatedAt(pointPolicy.getPointPolicyCreatedAt())
                .pointPolicyUpdatedAt(pointPolicy.getPointPolicyUpdatedAt())
                .pointPolicyState(pointPolicy.isPointPolicyState())
                .build();
    }

    @Override
    public void updatePointPolicyState(Long pointPolicyId, boolean isActive) {
        // 포인트 정책 상태 업데이트
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new IllegalArgumentException("PointPolicy not found"));

        pointPolicy.updatePointPolicyState(isActive);  // 상태 변경 메서드 호출
        pointPolicyRepository.save(pointPolicy);
    }

    @Override
    @Transactional
    public void deactivatePointPolicy(Long pointPolicyId) {
        // 포인트 정책 비활성화
        updatePointPolicyState(pointPolicyId, false); // 상태를 false로 설정하여 비활성화
    }

    @Transactional
    @Override
    public void activatePointPolicy(Long pointPolicyId) {
        // 포인트 정책 활성화
        updatePointPolicyState(pointPolicyId, true);  // 상태를 true로 설정하여 활성화
    }
}
