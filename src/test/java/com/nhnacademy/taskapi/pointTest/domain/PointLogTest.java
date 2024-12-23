package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PointLogTest {

    @Test
    void testBuilder() {
        Point point = new Point(100, new Member());
        PointLog pointLog = new PointLog(1L, LocalDateTime.now(), "Added", 50, point);

        assertNotNull(pointLog);
        assertEquals(50, pointLog.getPointLogAmount());
    }
}