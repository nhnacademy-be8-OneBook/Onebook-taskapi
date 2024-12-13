package com.nhnacademy.taskapi.points.service.impl;

import com.nhnacademy.taskapi.points.domain.Point;
import com.nhnacademy.taskapi.points.jpa.JpaPointRepository;
import com.nhnacademy.taskapi.points.repository.PointLogRepository;
import com.nhnacademy.taskapi.points.request.UpdatePointRequest;
import com.nhnacademy.taskapi.points.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.points.response.PointResponse;
import com.nhnacademy.taskapi.points.response.UpdatePointResponse;
import com.nhnacademy.taskapi.points.service.PointService;
import com.nhnacademy.taskapi.points.domain.PointLog;
import com.nhnacademy.taskapi.points.exception.PointPolicyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Service
public class PointServiceImpl implements PointService {

    private final JpaPointRepository pointRepository;
    private final PointLogRepository pointLogRepository;

    @Autowired
    public PointServiceImpl(JpaPointRepository pointRepository, PointLogRepository pointLogRepository) {
        this.pointRepository = pointRepository;
        this.pointLogRepository = pointLogRepository;
    }

    @Override
    public PointResponse findPointByMemberId(Long memberId) {
        // 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return new PointResponse(memberId, point.getAmount());
    }

    @Override
    public UpdatePointResponse updatePointByMemberId(Long memberId, UpdatePointRequest pointRequest) {
        // 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 포인트 갱신 로직
        int updatedAmount = point.getAmount() + pointRequest.getAmount();
        point.setAmount(updatedAmount);
        pointRepository.save(point);

        // 포인트 로그 기록 (포인트 변경 시)
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POINT_UPDATE")
                .pointLogAmount(pointRequest.getAmount()) // 추가된 포인트
                .point(point)
                .build();

        pointLogRepository.save(pointLog);

        return UpdatePointResponse.success(updatedAmount);  // 수정된 부분
    }

    @Override
    public void updatePointByRefund(Long memberId, UpdateRefundRequest refundRequest) {
        // 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 환불된 포인트 갱신
        int refundedAmount = point.getAmount() - refundRequest.getRefundAmount();
        if (refundedAmount < 0) {
            throw new PointPolicyException("환불 금액이 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        point.setAmount(refundedAmount);
        pointRepository.save(point);

        // 포인트 로그 기록 (환불 처리 시)
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POINT_REFUND")
                .pointLogAmount(-refundRequest.getRefundAmount()) // 환불된 포인트
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }
}
