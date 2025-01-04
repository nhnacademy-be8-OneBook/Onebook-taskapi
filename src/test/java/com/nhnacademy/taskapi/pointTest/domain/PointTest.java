package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointTest {
    private PointPolicy pointPolicy;
    private Member member;

    @BeforeEach
    void setUp() {
        pointPolicy = mock(PointPolicy.class);
        member = mock(Member.class);
    }

    @Test
    void testPointCreation() {
        Point point = new Point(1000, pointPolicy, member);

        assertNotNull(point);  // Point 객체가 null이 아님을 확인
        assertEquals(1000, point.getAmount());  // 초기 포인트 값이 1000인지 확인
        assertEquals(pointPolicy, point.getPointPolicy());  // 연결된 PointPolicy가 맞는지 확인
        assertEquals(member, point.getMember());  // 연결된 Member가 맞는지 확인
    }

    @Test
    void testUpdatePointCurrent() {
        Point point = new Point(1000, pointPolicy, member);
        point.updatePointCurrent(1500);

        assertEquals(1500, point.getAmount());  // 포인트 갱신 후 값이 1500인지 확인
    }

    @Test
    void testSetAmount() {
        Point point = new Point(1000, pointPolicy, member);
        point.setAmount(2000);

        assertEquals(2000, point.getAmount());  // setAmount 후 값이 2000인지 확인
    }

    @Test
    void testGetAmount() {
        Point point = new Point(1000, pointPolicy, member);
        assertEquals(1000, point.getAmount());  // getAmount 메소드가 1000을 반환하는지 확인
    }
}