package com.nhnacademy.taskapi.point.response;

import lombok.Builder;

@Builder
public record PointResponse(String memberId, int point) {
    // 포인트가 양수인지 확인
    public boolean isValidPoint() {
        return point >= 0; // 포인트는 음수일 수 없음
    }

    // 포인트가 일정 금액 이상일 때
    public String getPointStatusMessage() {
        if (point >= 1000) {
            return "충분한 포인트가 있습니다.";
        }
        else if (point >= 0) {
            return "포인트가 부족합니다.";
        }
        else {
            return "유효하지 않은 포인트 값입니다.";
        }
    }
}
