package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.exception.PointPolicyException;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import com.nhnacademy.taskapi.point.repository.PointLogRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {
    private final JpaPointPolicyRepository pointPolicyRepository;
    private final JpaPointRepository pointRepository;
    private final PointLogRepository pointLogRepository;

    // 포인트 정책 생성
    @Override
    public PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest) {
        // 정책 생성
        Member member = pointRepository.findById(policyRequest.memberId()) // Member를 실제 DB에서 조회
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)).getMember();

        PointPolicy pointPolicy = pointPolicyRepository.save(policyRequest.toEntity(member));

        // 포인트 로그 기록 (정책 생성 시 포인트 변화 없음)
        Point point = pointRepository.findByMember_Id(policyRequest.memberId())
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POLICY_CREATE")
                .pointLogAmount(0)  // 정책 생성 시 포인트 변화 없음
                .point(point)
                .build();

        pointLogRepository.save(pointLog);

        return PointPolicyResponse.create(pointPolicy, policyRequest);
    }

    // 포인트 정책 단건 조회
    @Transactional(readOnly = true)
    @Override
    public PointPolicyResponse findPointPolicyById(String pointPolicyId) {
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new PointPolicyException("포인트 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return PointPolicyResponse.find(pointPolicy);
    }

    // 포인트 정책 목록 조회
    @Transactional(readOnly = true)
    @Override
    public Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable) {
        Page<PointPolicy> pointPolicies = pointPolicyRepository.findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(pageable);
        return pointPolicies.map(PointPolicyResponse::find);
    }

    // 포인트 정책 수정
    @Override
    public PointPolicyResponse updatePointPolicyById(String pointPolicyId, PointPolicyRequest policyRequest) {
        // 기존 포인트 정책 조회
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new PointPolicyException("포인트 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 기존 정책 이름 변경
        String oldPolicyName = pointPolicy.getPointPolicyName();
        pointPolicy.updatePointPolicyName(policyRequest.pointPolicyName());

        // 정책 적용 금액 또는 비율 업데이트
        if (policyRequest.pointPolicyApplyType()) {
            pointPolicy.updatePointPolicyApplyAmount(Integer.valueOf(policyRequest.pointPolicyApply()));
            pointPolicy.updatePointPolicyConditionAmount(null);
            pointPolicy.updatePointPolicyRate(null);
        } else {
            pointPolicy.updatePointPolicyRate(Integer.valueOf(policyRequest.pointPolicyApply()));
            pointPolicy.updatePointPolicyConditionAmount(Integer.valueOf(policyRequest.pointPolicyConditionAmount()));
            pointPolicy.updatePointPolicyApplyAmount(null);
        }
        pointPolicy.updatePointPolicyCondition(policyRequest.pointPolicyCondition());
        pointPolicy.updatePointPolicyApplyType(policyRequest.pointPolicyApplyType());
        pointPolicy.updatePointPolicyUpdatedAt();
        pointPolicyRepository.save(pointPolicy);

        // 포인트 로그 기록 (정책 수정 시)
        Point point = pointRepository.findByMember_Id(pointPolicy.getMember().getId())
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POLICY_UPDATE")
                .pointLogAmount(0) // 정책 수정 시 포인트 변화 없음
                .point(point)
                .build();

        pointLogRepository.save(pointLog);

        return PointPolicyResponse.update(pointPolicy, policyRequest);
    }

    // 포인트 정책 삭제
    @Override
    public void deletePointPolicyById(String pointPolicyId) {
        // 정책 조회
        PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new PointPolicyException("포인트 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 정책 상태 변경 및 업데이트 시간 기록
        pointPolicy.updatePointPolicyState(false);
        pointPolicy.updatePointPolicyUpdatedAt();
        pointPolicyRepository.save(pointPolicy);

        // 포인트 로그 기록 (정책 삭제 시)
        Point point = pointRepository.findByMember_Id(pointPolicy.getMember().getId())
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POLICY_DELETE")
                .pointLogAmount(0)  // 정책 삭제 시 포인트 변화 없음
                .point(point)
                .build();
        pointLogRepository.save(pointLog);
    }
}