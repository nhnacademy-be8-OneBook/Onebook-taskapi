package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.exception.PointPolicyException;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import com.nhnacademy.taskapi.point.repository.PointLogRepository;
import com.nhnacademy.taskapi.point.request.UpdatePointRequest;
import com.nhnacademy.taskapi.point.request.UpdateRefundRequest;
import com.nhnacademy.taskapi.point.response.PointResponse;
import com.nhnacademy.taskapi.point.response.UpdatePointResponse;
import com.nhnacademy.taskapi.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    // 포인트 조회
    @Override
    public PointResponse findPointByMemberId(Long memberId) {
        // 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // PointResponse로 포인트 정보 반환
        return new PointResponse(memberId, point.getAmount());
    }

    @Override
    public UpdatePointResponse updatePointByMemberId(Long memberId, UpdatePointRequest pointRequest) {
        // 1. 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 2. 포인트 갱신: 요청된 포인트 금액을 더하거나 빼기
        int updatedAmount = point.getAmount() + pointRequest.getAmount();  // 기존 포인트에 요청된 금액을 추가 또는 차감
        point.setAmount(updatedAmount);

        // 3. 포인트 저장
        pointRepository.save(point);

        // 4. 포인트 로그 기록 (변경된 포인트)
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())  // 현재 시간
                .pointLogUpdatedType("POINT_UPDATE")    // 변경 타입
                .pointLogAmount(pointRequest.getAmount())  // 변경된 포인트 금액
                .point(point)  // 연관된 포인트
                .build();
        pointLogRepository.save(pointLog);  // 포인트 로그 저장

        // 5. 갱신된 포인트 정보를 포함한 응답 반환
        return UpdatePointResponse.success(updatedAmount);
    }


    // 포인트 결제
    @Override
    public void usePointsForPayment(Long memberId, int paymentAmount) {
        // 포인트 조회
        Point point = pointRepository.findByMember_MemberId(memberId)
                .orElseThrow(() -> new PointPolicyException("사용자 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (point.getAmount() < paymentAmount) {
            throw new PointPolicyException("포인트가 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        // 포인트 차감
        point.setAmount(point.getAmount() - paymentAmount);
        pointRepository.save(point);

        // 포인트 로그 기록 (포인트 결제)
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("POINT_PAYMENT")
                .pointLogAmount(-paymentAmount)  // 결제된 포인트
                .point(point)
                .build();
        pointLogRepository.save(pointLog);
    }

    // 포인트 환불 처리
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
