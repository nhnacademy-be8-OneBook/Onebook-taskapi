package com.nhnacademy.taskapi.point.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public record PointLogResponse(
        int pointCurrent,  // 현재 포인트
        String pointLogUpdatedType,  // 포인트 업데이트 타입 (ex. 증가, 감소 등)
        int pointLogAmount,  // 포인트 변화량
        LocalDateTime pointLogUpdatedAt  // 포인트 변경 시각
) {

        // 포인트 업데이트 시각을 지정된 포맷으로 문자열로 반환하는 메서드
        public String getFormattedUpdatedAt() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return pointLogUpdatedAt.format(formatter);
        }

        // 포인트 업데이트 타입이 유효한지 확인하는 메서드 (예시: 증가, 감소만 허용)
        public boolean isValidUpdatedType() {
                return "증가".equals(pointLogUpdatedType) || "감소".equals(pointLogUpdatedType);
        }
}