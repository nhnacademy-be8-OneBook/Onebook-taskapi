package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PointLogTest {

    @Test
    void testPointLogBuilder() {
        // Point 객체 생성
        Point point = new Point(1000, null, null);  // Point 객체는 간단하게 생성 (PointPolicy 및 Member는 null로 설정)

        // PointLog 객체 생성
        PointLog pointLog = PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("PURCHASE")  // 예시로 "PURCHASE" 타입 설정
                .pointLogAmount(200)  // 포인트 변경 금액
                .point(point)  // Point 객체 연결
                .build();

        // Assertions
        assertNotNull(pointLog);  // pointLog 객체가 null이 아님을 확인
        assertEquals(200, pointLog.getPointLogAmount());  // 포인트 변경 액션 금액이 200인지 확인
        assertEquals("PURCHASE", pointLog.getPointLogUpdatedType());  // 타입이 "PURCHASE"인지 확인
        assertNotNull(pointLog.getPoint());  // Point 객체가 null이 아님을 확인
        assertEquals(1000, pointLog.getPoint().getAmount());  // Point 객체의 포인트 금액이 1000인지 확인
    }

    @Test
    void testPointLogCreation() {
        // Point 객체 생성
        Point point = new Point(5000, null, null);  // Point 객체 간단히 생성

        // PointLog 객체 생성
        LocalDateTime currentTime = LocalDateTime.now();
        PointLog pointLog = new PointLog(
                null,  // pointLogId는 null로 설정 (자동 생성 예정)
                currentTime,  // 변경 시간
                "REWARD",  // 변경 타입
                300,  // 포인트 갱신액
                point  // 연결된 Point 객체
        );

        // Assertions
        assertNotNull(pointLog);  // pointLog 객체가 null이 아님을 확인
        assertEquals(300, pointLog.getPointLogAmount());  // 포인트 갱신액이 300인지 확인
        assertEquals("REWARD", pointLog.getPointLogUpdatedType());  // 포인트 변경 타입이 "REWARD"인지 확인
        assertEquals(currentTime, pointLog.getPointLogUpdatedAt());  // 포인트 변경 시간 확인
        assertNotNull(pointLog.getPoint());  // Point 객체가 null이 아님을 확인
        assertEquals(5000, pointLog.getPoint().getAmount());  // Point 객체의 금액이 5000인지 확인
    }
}
