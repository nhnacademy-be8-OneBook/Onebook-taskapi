package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.exception.PointPolicyException;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
import com.nhnacademy.taskapi.point.repository.PointLogRepository;
import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PointServiceImpl implements PointService {
    private final JpaPointRepository pointRepository;
    private final JpaPointPolicyRepository pointPolicyRepository;
    private final PointLogRepository pointLogRepository;

    public PointServiceImpl(JpaPointRepository pointRepository,
                            JpaPointPolicyRepository pointPolicyRepository,
                            PointLogRepository pointLogRepository) {
        this.pointRepository = pointRepository;
        this.pointPolicyRepository = pointPolicyRepository;
        this.pointLogRepository = pointLogRepository;
    }

    /**
     * 회원가입 시 포인트 적립 (기본 5000포인트)
     */
    @Override
    public void registerMemberPoints(Member member) {
        Point point = new Point(5000, member);  // 5000포인트 적립
        pointRepository.save(point);

        // 포인트 로그 기록
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("REGISTRATION")  // 회원가입으로 적립
                .pointLogAmount(5000)
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }

    /**
     * 리뷰 작성 시 포인트 적립 (기본 200포인트, 사진 첨부 시 500포인트)
     */
    @Override
    public void registerReviewPoints(Member member, boolean isPhotoAttached) {
        Optional<Point> optionalPoint = pointRepository.findByMember_Id(member.getId());
        Point point = optionalPoint.orElseThrow(() -> new PointPolicyException("회원 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 기본 200포인트 적립, 사진이 첨부되었다면 500포인트 적립
        int pointAmount = isPhotoAttached ? 500 : 200;

        point.updatePointCurrent(point.getAmount() + pointAmount);  // 리뷰 작성시 포인트 추가
        pointRepository.save(point);

        // 포인트 로그 기록
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("REVIEW")  // 리뷰 작성으로 적립
                .pointLogAmount(pointAmount)
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }

    /**
     * 도서 결제 시 포인트 적립 (기본 적립률 적용)
     * 결제 금액 * 기본 적립률에 의한 포인트 계산
     */
    @Override
    public void registerPurchasePoints(Member member, int purchaseAmount) {
        PointPolicy pointPolicy = getDefaultPointPolicy();  // 기본 포인트 정책을 가져옴
        int earnedPoints = calculatePointsBasedOnPolicy(purchaseAmount, pointPolicy);

        Optional<Point> optionalPoint = pointRepository.findByMember_Id(member.getId());
        Point point = optionalPoint.orElseThrow(() -> new PointPolicyException("회원 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        point.updatePointCurrent(point.getAmount() + earnedPoints);  // 결제금액에 대한 포인트 추가
        pointRepository.save(point);

        // 포인트 로그 기록
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("PURCHASE")  // 도서 구매로 적립
                .pointLogAmount(earnedPoints)
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }

    /**
     * 기본 포인트 정책을 가져옵니다.
     * (예시로 가장 최근에 활성화된 포인트 정책을 가져옴)
     */
    private PointPolicy getDefaultPointPolicy() {
        return pointPolicyRepository.findAll()
                .stream()
                .filter(PointPolicy::isActive)  // isActive() 메소드를 사용
                .findFirst()
                .orElseThrow(() -> new PointPolicyException("적용 가능한 포인트 정책이 없습니다.", HttpStatus.BAD_REQUEST));
    }

    /**
     * 결제 금액에 따른 포인트를 계산합니다.
     * (구체적인 로직은 포인트 정책에 따라 달라짐)
     */
    private int calculatePointsBasedOnPolicy(int purchaseAmount, PointPolicy pointPolicy) {
        if (pointPolicy.isPointPolicyApplyType()) {
            // 적립률 비율 기반으로 포인트 계산
            return (purchaseAmount * pointPolicy.getPointPolicyRate()) / 100;
        } else {
            // 고정 적립금액 기반으로 포인트 계산
            return pointPolicy.getPointPolicyApplyAmount();
        }
    }

    /**
     * 관리자에 의해 포인트 정책 수정
     */
    @Override
    public void updatePointPolicy(Long pointPolicyId, PointPolicy pointPolicy) {
        PointPolicy existingPolicy = pointPolicyRepository.findById(String.valueOf(pointPolicyId))
                .orElseThrow(() -> new PointPolicyException("포인트 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        existingPolicy.updatePointPolicyRate(pointPolicy.getPointPolicyRate());
        existingPolicy.updatePointPolicyApplyAmount(pointPolicy.getPointPolicyApplyAmount());
        pointPolicyRepository.save(existingPolicy);
    }

    /**
     * 포인트 결제 기능
     *
     * @param memberId      - 회원 ID
     * @param paymentAmount - 결제 금액
     * @throws PointPolicyException - 포인트가 부족한 경우 예외 발생
     */
    public void usePointsForPayment(String memberId, int paymentAmount) {
        // 회원의 포인트 정보 가져오기
        Optional<Point> optionalPoint = pointRepository.findByMember_Id(Long.valueOf(memberId));
        Point point = optionalPoint.orElseThrow(() -> new PointPolicyException("회원 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 결제 금액이 포인트보다 많은지 확인
        if (point.getAmount() < paymentAmount) {
            throw new PointPolicyException("포인트가 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        // 결제 금액만큼 포인트 차감
        point.updatePointCurrent(point.getAmount() - paymentAmount);  // 포인트 차감
        pointRepository.save(point);

        // 포인트 로그 기록
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("PAYMENT")  // 결제 타입
                .pointLogAmount(-paymentAmount)  // 차감된 포인트는 마이너스
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }

    /**
     * 포인트 환불/반품 기능
     *
     * @param memberId     - 회원 ID
     * @param refundAmount - 환불 금액
     * @throws PointPolicyException - 포인트가 부족한 경우 예외 발생
     */
    public void updatePointByRefund(String memberId, int refundAmount) {
        // 회원의 포인트 정보 가져오기
        Optional<Point> optionalPoint = pointRepository.findByMember_Id(Long.valueOf(memberId));
        Point point = optionalPoint.orElseThrow(() -> new PointPolicyException("회원 포인트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 환불 금액이 포인트보다 많은지 확인
        if (point.getAmount() < refundAmount) {
            throw new PointPolicyException("환불 금액이 부족합니다.", HttpStatus.BAD_REQUEST);
        }

        // 환불 금액만큼 포인트 복구
        point.updatePointCurrent(point.getAmount() + refundAmount);  // 포인트 복구
        pointRepository.save(point);

        // 포인트 로그 기록
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("REFUND")  // 환불 타입
                .pointLogAmount(refundAmount)  // 환불된 포인트
                .point(point)
                .build();

        pointLogRepository.save(pointLog);
    }
}