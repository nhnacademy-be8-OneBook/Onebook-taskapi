package com.nhnacademy.taskapi.pointTest;

import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.grade.domain.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private Point point;
    private Member member;
    private Customer customer;
    private Grade grade;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        grade = new Grade();
        grade.setName("Gold");
        grade.setAccumulationRate(10);
        grade.setDescription("Gold Level");

        member = new Member(customer, grade, "testUser", "password123", LocalDate.of(1990, 1, 1), Member.Gender.M, "123-456-7890");

        point = Point.builder()
                .pointCurrent(1000)  // 초기 포인트
                .member(member)
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
}
