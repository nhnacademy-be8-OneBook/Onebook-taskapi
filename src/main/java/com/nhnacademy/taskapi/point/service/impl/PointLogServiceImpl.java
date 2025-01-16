package com.nhnacademy.taskapi.point.service.impl;

import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.repository.PointLogRepository;
import com.nhnacademy.taskapi.point.dto.PointLogResponse;
import com.nhnacademy.taskapi.point.service.PointLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointLogServiceImpl implements PointLogService {

    private final PointLogRepository pointLogRepository;

    // 생성자 주입을 통해 PointLogRepository를 의존성으로 받습니다.
    public PointLogServiceImpl(PointLogRepository pointLogRepository) {
        this.pointLogRepository = pointLogRepository;
    }

    /**
     * 회원 ID에 해당하는 포인트 로그를 페이지네이션으로 조회
     *
     * @param member_id - 회원 ID
     * @param pageable  - 페이징 정보 (page size, page number 등)
     * @return Page<PointLogResponse> - 포인트 로그 응답 객체
     */
    @Override
    public Page<PointLogResponse> findAllPointLogsByMemberId(Long member_id, Pageable pageable) {
        // memberId에 해당하는 포인트 로그를 Pageable 형식으로 조회
        Page<PointLog> pointLogs = pointLogRepository.findByPoint_Member_Id(member_id, pageable);

        // PointLog 엔티티를 PointLogResponse DTO로 변환하여 반환
        return pointLogs.map(pointLog -> PointLogResponse.builder()
                .pointCurrent(pointLog.getPoint().getAmount()) // 해당 포인트의 현재 금액
                .pointLogUpdatedType(String.valueOf(pointLog.getPointLogUpdatedType()))
                .pointLogAmount(pointLog.getPointLogAmount())
                .pointLogUpdatedAt(pointLog.getPointLogUpdatedAt())
                .build());
    }
}