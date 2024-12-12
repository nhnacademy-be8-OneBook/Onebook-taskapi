package com.nhnacademy.taskapi.point.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public record PointLogResponse (
        int pointCurrent,  // 현재 포인트
        String pointLogUpdatedType,  // 포인트 업데이트 타입 (ex. 증가, 감소 등)
        int pointLogAmount,  // 포인트 변화량
        LocalDateTime pointLogUpdatedAt  // 포인트 변경 시각
        ) { }
