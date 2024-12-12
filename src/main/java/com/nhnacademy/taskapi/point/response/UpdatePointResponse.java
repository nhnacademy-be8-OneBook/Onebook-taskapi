package com.nhnacademy.taskapi.point.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record UpdatePointResponse(
        int point,
        String message
) {

    // 포인트 상태 메시지 설정
    public static UpdatePointResponse withMessage(int point, String message) {
        return UpdatePointResponse.builder()
                .point(point)
                .message(message)
                .build();
    }

    // 포인트 변화 정상
    public static UpdatePointResponse success(int point) {
        return withMessage(point, "포인트가 성공적으로 업데이트되었습니다.");
    }

    // 포인트 변화 실패
    public static UpdatePointResponse failure(String message) {
        return withMessage(0, message);  // 실패 시 포인트는 0으로 설정
    }
}
