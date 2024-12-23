package com.nhnacademy.taskapi.pointTest.exception;

import com.nhnacademy.taskapi.point.exception.PointPolicyException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class PointExceptionTest {

    // 예외 메시지와 상태 코드 확인 (PointPolicyException 생성 시)
    @Test
    void testPointPolicyExceptionMessage() {
        String errorMessage = "포인트가 부족합니다.";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        PointPolicyException exception = PointPolicyException.badRequest(errorMessage);

        // 예외 메시지 확인
        assertEquals(errorMessage, exception.getMessage());
        // 상태 코드 확인
        assertEquals(status, exception.getStatus());
    }

    // 예외 메시지와 상태 코드 확인 (내부 서버 오류)
    @Test
    void testPointPolicyExceptionInternalServerError() {
        String errorMessage = "Internal server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        PointPolicyException exception = PointPolicyException.internalServerError();

        // 예외 메시지 확인
        assertEquals(errorMessage, exception.getMessage());

        // 상태 코드 확인
        assertEquals(status, exception.getStatus());
    }

    // PointPolicyException의 생성자 사용 시 상태 코드가 잘 설정되는지 확인
    @Test
    void testPointPolicyExceptionConstructor() {
        String errorMessage = "Point policy not found";
        HttpStatus status = HttpStatus.NOT_FOUND;

        PointPolicyException exception = PointPolicyException.pointPolicyNotFound();

        // 예외 메시지 확인
        assertEquals(errorMessage, exception.getMessage());
        // 상태 코드 확인
        assertEquals(status, exception.getStatus());
    }
}
