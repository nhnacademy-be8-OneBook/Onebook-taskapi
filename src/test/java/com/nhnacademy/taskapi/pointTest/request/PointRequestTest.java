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
        // Given
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
        assertNotNull(pointPolicy, "PointPolicy 객체는 null이 아니어야 합니다.");
        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName(), "PointPolicyName 값이 일치하지 않습니다.");
        assertEquals(pointPolicyRate, pointPolicy.getPointPolicyRate(), "PointPolicyRate 값이 일치하지 않습니다.");
        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount(), "PointPolicyConditionAmount 값이 일치하지 않습니다.");
        assertEquals(pointPolicyApplyAmount, pointPolicy.getPointPolicyApplyAmount(), "PointPolicyApplyAmount 값이 일치하지 않습니다.");
        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition(), "PointPolicyCondition 값이 일치하지 않습니다.");
        assertEquals(pointPolicyApplyType, pointPolicy.isPointPolicyApplyType(), "PointPolicyApplyType 값이 일치하지 않습니다.");
        assertTrue(pointPolicy.isPointPolicyState(), "PointPolicyState 기본값은 true여야 합니다.");
        assertNotNull(pointPolicy.getPointPolicyCreatedAt(), "PointPolicyCreatedAt 값은 null이 아니어야 합니다.");
        assertTrue(
                pointPolicy.getPointPolicyCreatedAt().isBefore(LocalDateTime.now().plusSeconds(5)),
                "PointPolicyCreatedAt 값이 현재 시간 이전이어야 합니다."
        );
    }

    // 2. PointPolicyRequest 테스트
    @Test
    void testPointPolicyRequest() {
        // Given
        String name = "Updated Policy";
        Integer rate = 15;
        Integer conditionAmount = 2000;
        Integer applyAmount = 200;
        String condition = "Updated Condition";
        boolean applyType = false;

        PointPolicyRequest request = PointPolicyRequest.builder()
                .pointPolicyName(name)
                .pointPolicyRate(rate)
                .pointPolicyConditionAmount(conditionAmount)
                .pointPolicyApplyAmount(applyAmount)
                .pointPolicyCondition(condition)
                .pointPolicyApplyType(applyType)
                .build();

        // When
        PointPolicy pointPolicy = request.toEntity();

        // Then
        assertNotNull(pointPolicy, "PointPolicy 객체는 null이 아니어야 합니다.");
        assertEquals(name, pointPolicy.getPointPolicyName(), "PointPolicyName 값이 일치하지 않습니다.");
        assertEquals(rate, pointPolicy.getPointPolicyRate(), "PointPolicyRate 값이 일치하지 않습니다.");
        assertEquals(conditionAmount, pointPolicy.getPointPolicyConditionAmount(), "PointPolicyConditionAmount 값이 일치하지 않습니다.");
        assertEquals(applyAmount, pointPolicy.getPointPolicyApplyAmount(), "PointPolicyApplyAmount 값이 일치하지 않습니다.");
        assertEquals(condition, pointPolicy.getPointPolicyCondition(), "PointPolicyCondition 값이 일치하지 않습니다.");
        assertEquals(applyType, pointPolicy.isPointPolicyApplyType(), "PointPolicyApplyType 값이 일치하지 않습니다.");
        assertTrue(pointPolicy.isPointPolicyState(), "PointPolicyState 기본값은 true여야 합니다.");
        assertNotNull(pointPolicy.getPointPolicyCreatedAt(), "PointPolicyCreatedAt 값은 null이 아니어야 합니다.");
    }

    // 3. PointPolicyRequest 기본값 처리 테스트
    @Test
    void testPointPolicyRequestWithNullValues() {
        // Given
        PointPolicyRequest request = PointPolicyRequest.builder()
                .pointPolicyName("Default Policy")
                .pointPolicyRate(null)
                .pointPolicyConditionAmount(null)
                .pointPolicyApplyAmount(null)
                .pointPolicyCondition(null)
                .pointPolicyApplyType(true)
                .build();

        // When
        PointPolicy pointPolicy = request.toEntity();

        // Then
        assertNotNull(pointPolicy, "PointPolicy 객체는 null이 아니어야 합니다.");
        assertEquals("Default Policy", pointPolicy.getPointPolicyName(), "PointPolicyName 값이 일치하지 않습니다.");
        assertEquals(0, pointPolicy.getPointPolicyRate(), "null이 0으로 변환되지 않았습니다.");
        assertEquals(0, pointPolicy.getPointPolicyConditionAmount(), "null이 0으로 변환되지 않았습니다.");
        assertEquals(0, pointPolicy.getPointPolicyApplyAmount(), "null이 0으로 변환되지 않았습니다.");
        assertNull(pointPolicy.getPointPolicyCondition(), "null은 그대로 null이어야 합니다.");
        assertTrue(pointPolicy.isPointPolicyApplyType(), "PointPolicyApplyType 값이 일치하지 않습니다.");
        assertTrue(pointPolicy.isPointPolicyState(), "PointPolicyState 기본값은 true여야 합니다.");
        assertNotNull(pointPolicy.getPointPolicyCreatedAt(), "PointPolicyCreatedAt 값은 null이 아니어야 합니다.");
    }
}