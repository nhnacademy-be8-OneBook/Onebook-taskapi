//package com.nhnacademy.taskapi.pointTest.domain;
//
//import com.nhnacademy.taskapi.member.domain.Member;
//import com.nhnacademy.taskapi.point.domain.Point;
//import com.nhnacademy.taskapi.point.domain.PointLog;
//import com.nhnacademy.taskapi.point.domain.PointLogUpdatedType;  // PointLogUpdatedType을 import 해야 함
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PointLogTest {
//    @Test
//    void testBuilder() {
//        // Point 객체 생성 (member 객체도 필요)
//        Point point = new Point(100, new Member());
//
//        // PointLog 객체 생성 시 PointLogUpdatedType을 enum으로 사용
//        PointLog pointLog = new PointLog(1L, LocalDateTime.now(), PointLogUpdatedType.REGISTRATION, 50, point);
//
//        // Assertion 체크
//        assertNotNull(pointLog);
//        assertEquals(50, pointLog.getPointLogAmount());
//    }
//}
