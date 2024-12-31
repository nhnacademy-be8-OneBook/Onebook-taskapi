package com.nhnacademy.taskapi.pointTest.request;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PointRequestTest {

    // 1. CreatePointPolicyRequest 테스트
    @Test
    void testCreatePointPolicyRequest() {
        String pointPolicyName = "Test Policy";
        int pointPolicyRate = 10;
        int pointPolicyConditionAmount = 100;
        int pointPolicyApplyAmount = 50;
        String pointPolicyCondition = "Test Condition";
        boolean pointPolicyApplyType = true;

        CreatePointPolicyRequest request = CreatePointPolicyRequest.builder()
                .pointPolicyName(pointPolicyName)
                .pointPolicyRate(pointPolicyRate)
                .pointPolicyConditionAmount(pointPolicyConditionAmount)
                .pointPolicyApplyAmount(pointPolicyApplyAmount)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyApplyType(pointPolicyApplyType)
                .build();

        // When
        PointPolicy pointPolicy = request.toEntity();

        // Then
        assertNotNull(pointPolicy);
        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName());
        assertEquals(pointPolicyRate, pointPolicy.getPointPolicyRate());
        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount());
        assertEquals(pointPolicyApplyAmount, pointPolicy.getPointPolicyApplyAmount());
        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition());
        assertEquals(pointPolicyApplyType, pointPolicy.isPointPolicyApplyType());
        assertTrue(pointPolicy.isPointPolicyState());  // 기본적으로 true
        assertNotNull(pointPolicy.getPointPolicyCreatedAt());  // 생성일이 null이 아니어야 함
        assertTrue(pointPolicy.getPointPolicyCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    // 2. PointPolicyRequest 테스트
    @Test
    void testPointPolicyRequest() {
        // Given: PointPolicyRequest를 사용하여 객체를 생성합니다.
        String name = "Updated Policy";
        Integer rate = 15;
        Integer conditionAmount = 2000;
        Integer applyAmount = 200;
        String condition = "Updated Condition";
        boolean applyType = false;

        // PointPolicyRequest의 필드 값들을 설정합니다.
        PointPolicyRequest request = PointPolicyRequest.builder()
                .pointPolicyName(name)
                .pointPolicyRate(rate)
                .pointPolicyConditionAmount(conditionAmount)
                .pointPolicyApplyAmount(applyAmount)
                .pointPolicyCondition(condition)
                .pointPolicyApplyType(applyType)
                .build();

        // When: toEntity()를 호출하여 PointPolicy 엔티티로 변환합니다.
        PointPolicy pointPolicy = request.toEntity();

        // Then: PointPolicy 객체가 올바르게 생성되었는지 확인합니다.
        assertNotNull(pointPolicy);
        assertEquals(name, pointPolicy.getPointPolicyName());
        assertEquals(rate, pointPolicy.getPointPolicyRate());
        assertEquals(conditionAmount, pointPolicy.getPointPolicyConditionAmount());
        assertEquals(applyAmount, pointPolicy.getPointPolicyApplyAmount());
        assertEquals(condition, pointPolicy.getPointPolicyCondition());
        assertEquals(applyType, pointPolicy.isPointPolicyApplyType());
        assertTrue(pointPolicy.isPointPolicyState()); // 기본값 true 확인
        assertNotNull(pointPolicy.getPointPolicyCreatedAt()); // 현재 시간인지 확인 (null이 아니어야 함)
    }

    // 3. PointPolicyRequest 기본값 처리 테스트
    @Test
    void testPointPolicyRequestWithNullValues() {
        // Given: 일부 필드를 null로 설정하여 PointPolicyRequest 객체를 생성합니다.
        PointPolicyRequest request = PointPolicyRequest.builder()
                .pointPolicyName("Default Policy")
                .pointPolicyRate(null)  // null로 설정
                .pointPolicyConditionAmount(null)  // null로 설정
                .pointPolicyApplyAmount(null)  // null로 설정
                .pointPolicyCondition(null) // null로 설정
                .pointPolicyApplyType(true) // 기본값을 설정
                .build();

        // When: toEntity()를 호출하여 PointPolicy 엔티티로 변환합니다.
        PointPolicy pointPolicy = request.toEntity();

        // Then: null 값들이 0으로 처리되었는지 확인합니다.
        assertNotNull(pointPolicy);
        assertEquals("Default Policy", pointPolicy.getPointPolicyName());
        assertEquals(0, pointPolicy.getPointPolicyRate()); // null이 0으로 변환되었는지 확인
        assertEquals(0, pointPolicy.getPointPolicyConditionAmount()); // null이 0으로 변환되었는지 확인
        assertEquals(0, pointPolicy.getPointPolicyApplyAmount()); // null이 0으로 변환되었는지 확인
        assertNull(pointPolicy.getPointPolicyCondition()); // null은 그대로 null이어야 함
        assertTrue(pointPolicy.isPointPolicyApplyType()); // true는 그대로 true여야 함
        assertTrue(pointPolicy.isPointPolicyState()); // 기본값 true 확인
        assertNotNull(pointPolicy.getPointPolicyCreatedAt()); // 현재 시간인지 확인 (null이 아니어야 함)
    }

}
