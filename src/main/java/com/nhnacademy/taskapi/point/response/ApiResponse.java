package com.nhnacademy.taskapi.point.response;

public record ApiResponse<T>(T body, int status) {

    // 성공 응답을 생성하는 정적 메서드
    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>(body, 200);  // HTTP 200 OK 상태
    }

    // 실패 응답을 생성하는 정적 메서드
    public static <T> ApiResponse<T> failure(int status, T body) {
        return new ApiResponse<>(body, status);
    }

    // 상태 코드가 성공인지를 확인하는 메서드
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }

    // 상태 코드가 실패인지를 확인하는 메서드
    public boolean isFailure() {
        return status < 200 || status >= 300;
    }
}
