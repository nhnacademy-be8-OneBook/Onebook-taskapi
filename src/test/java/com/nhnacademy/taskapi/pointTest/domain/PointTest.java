package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.roles.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private Point point;
    private Member member;
    private Grade grade;

    @BeforeEach
    void setUp() {
        // Grade 객체 생성
        grade = Grade.create("Gold", 10, "Gold Level");

        // Member 객체 생성 (createNewMember 사용)
        member = Member.createNewMember(
                grade,
                "testUser",
                "testUserLogin",
                "password123",
                LocalDate.of(1990, 1, 1),
                Member.Gender.M,
                "testuser@example.com",
                "123-456-7890",
                new Role() // You should mock or create a valid Role object here if needed
        );

        // Point 객체 생성
        point = Point.builder()
                .pointCurrent(1000)  // 초기 포인트 1000
                .member(member)      // Member 객체 설정
                .build();
    }

    // 포인트 getter와 setter 테스트
    @Test
    void testGetAndSetPoint() {
        // 초기 포인트가 1000이어야 함
        assertEquals(1000, point.getAmount());
        // 포인트 설정 후 다시 확인
        point.setAmount(1500);
        assertEquals(1500, point.getAmount());
    }

    // 포인트 계산 로직 테스트 (예: 포인트 차감 후 금액이 맞는지 확인)
    @Test
    void testPointAfterDeduction() {
        int paymentAmount = 500;

        // 포인트 차감
        point.setAmount(point.getAmount() - paymentAmount);

        // 차감된 후의 포인트가 맞는지 확인
        assertEquals(500, point.getAmount());
    }

    // 포인트 증가 테스트
    @Test
    void testPointIncrease() {
        int additionalPoints = 300;

        // 포인트 증가
        point.setAmount(point.getAmount() + additionalPoints);

        // 증가된 포인트가 맞는지 확인
        assertEquals(1300, point.getAmount());
    }

    // 포인트 초기화 테스트
    @Test
    void testResetPoint() {
        point.setAmount(0);

        // 포인트가 0으로 초기화 되었는지 확인
        assertEquals(0, point.getAmount());
    }

    @Test
    void testUpdatePointCurrent() {
        Point point = new Point(100, new Member());  // Create a member object or mock it
        point.updatePointCurrent(150);

        assertThat(point.getAmount()).isEqualTo(150);
    }

    @Test
    void testSetAmount() {
        Point point = new Point(100, new Member());
        point.setAmount(200);

        assertThat(point.getAmount()).isEqualTo(200);
    }
}