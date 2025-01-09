package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.repository.PointPolicyRepository;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PointPolicyServiceImpl implements PointPolicyService {

    private final PointPolicyRepository pointPolicyRepository;

    @Autowired
    public PointPolicyServiceImpl(PointPolicyRepository pointPolicyRepository) {
        this.pointPolicyRepository = pointPolicyRepository;
    }

    @Override
    public PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest) {
        // CreatePointPolicyRequest에서 필요한 필드를 받아 PointPolicy Entity로 변환
        PointPolicy pointPolicy = policyRequest.toEntity();
        pointPolicy.updatePointPolicyCreatedAt(LocalDateTime.now()); // 생성일을 현재 시간으로 설정
        pointPolicy.updatePointPolicyState(true); // 기본적으로 활성 상태 설정
        pointPolicyRepository.save(pointPolicy); // DB에 저장

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
                .build();  // Response로 변환하여 반환
    }

    @Override
    public PointPolicyResponse findPointPolicyById(Long pointPolicyId) {
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid point policy id: " + pointPolicyId));

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
                .build();  // Response로 변환하여 반환
    }

    @Override
    public Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable) {
        return pointPolicyRepository.findAll(pageable).map(pointPolicy -> PointPolicyResponse.builder()
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
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid point policy id: " + pointPolicyId));

        // PointPolicy 업데이트
        pointPolicy.updatePointPolicyName(policyRequest.pointPolicyName());  // record 필드로 접근
        pointPolicy.updatePointPolicyConditionAmount(policyRequest.pointPolicyConditionAmount());
        pointPolicy.updatePointPolicyApplyAmount(policyRequest.pointPolicyApplyAmount());
        pointPolicy.updatePointPolicyRate(policyRequest.pointPolicyRate());
        pointPolicy.updatePointPolicyCondition(policyRequest.pointPolicyCondition());
        pointPolicy.updatePointPolicyApplyType(policyRequest.pointPolicyApplyType());
        pointPolicy.updatePointPolicyState(true);  // 상태를 true로 설정 (위에서 조건에 맞는 값을 넣을 수 있음)
        // pointPolicy.updatePointPolicyUpdatedAt(); // 이 부분을 제거합니다. @PreUpdate로 자동 처리됩니다.

        pointPolicyRepository.save(pointPolicy); // 업데이트된 정보 저장

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
                .build();  // Response로 변환하여 반환
    }
    @Override
    public void deletePointPolicyById(Long pointPolicyId) {
        pointPolicyRepository.deleteById(pointPolicyId);
    }
}