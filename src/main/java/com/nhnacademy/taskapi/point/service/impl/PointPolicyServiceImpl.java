package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
//import com.nhnacademy.taskapi.point.exception.PointPolicyException;
//import com.nhnacademy.taskapi.point.exception.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {

   private final JpaPointPolicyRepository pointPolicyRepository;

   // 포인트 정책 생성
   @Override
   public PointPolicyResponse createPointPolicy(CreatePointPolicyRequest policyRequest) {

       PointPolicy pointPolicy = pointPolicyRepository.save(policyRequest.toEntity());

       return PointPolicyResponse.create(pointPolicy, policyRequest);
   }

   // 포인트 정책 단건 조회
   @Transactional(readOnly = true)
   @Override
   public PointPolicyResponse findPointPolicyById(Long pointPolicyId) {

       PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
               .orElseThrow(() -> new PointPolicyException(ErrorStatus.toErrorStatus("포인트 정책을 찾을 수 없습니다.", 400, LocalDateTime.now())));

       return PointPolicyResponse.find(pointPolicy);
   }

   // 포인트 정책 목록 조회
   @Transactional(readOnly = true)
   @Override
   public Page<PointPolicyResponse> findAllPointPolicies(Pageable pageable) {

       Page<PointPolicy> pointPolicies = pointPolicyRepository.findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(pageable);

       return pointPolicies.map(PointPolicyResponse::find);
   }

   @Override
   public PointPolicyResponse updatePointPolicyById(Long pointPolicyId, PointPolicyRequest policyRequest) {

       PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
               .orElseThrow(() -> new PointPolicyException(ErrorStatus.toErrorStatus("포인트 정책을 찾을 수 없습니다.", 400, LocalDateTime.now())));

       pointPolicy.updatePointPolicyName(policyRequest.pointPolicyName());

       if (policyRequest.pointPolicyApplyType()) {
           pointPolicy.updatePointPolicyApplyAmount(policyRequest.pointPolicyApply());
           pointPolicy.updatePointPolicyConditionAmount(null);
           pointPolicy.updatePointPolicyRate(null);
       }
       else {
           pointPolicy.updatePointPolicyRate(policyRequest.pointPolicyApply());
           pointPolicy.updatePointPolicyConditionAmount(policyRequest.pointPolicyConditionAmount());
           pointPolicy.updatePointPolicyApplyAmount(null);
       }

       pointPolicy.updatePointPolicyCondition(policyRequest.pointPolicyCondition());
       pointPolicy.updatePointPolicyApplyType(policyRequest.pointPolicyApplyType());

       pointPolicy.updatePointPolicyUpdatedAt();

       pointPolicyRepository.save(pointPolicy);

       return PointPolicyResponse.update(pointPolicy, policyRequest);
   }

   @Override
   public void deletePointPolicyById(Long pointPolicyId) {

       PointPolicy pointPolicy = pointPolicyRepository.findById(pointPolicyId)
               .orElseThrow(() -> new PointPolicyException(ErrorStatus.toErrorStatus("포인트 정책을 찾을 수 없습니다.", 400, LocalDateTime.now())));

       pointPolicy.updatePointPolicyState(false);
       pointPolicy.updatePointPolicyUpdatedAt();

       pointPolicyRepository.save(pointPolicy);
   }
}
