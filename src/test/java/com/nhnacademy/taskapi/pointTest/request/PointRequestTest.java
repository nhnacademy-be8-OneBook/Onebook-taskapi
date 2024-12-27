package com.nhnacademy.taskapi.pointTest.request;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointRequestTest {

    Member member = new Member();

    // CreatePointPolicyRequest에 대한 테스트
    @Test
    public void testCreatePointPolicyRequestToEntity() {
        // Given
        Long memberId = 1L;
        String pointPolicyName = "Test Policy";
        int pointPolicyApply = 1000;
        String pointPolicyCondition = "조건1";
        boolean pointPolicyApplyType = true;  // Apply Amount
        int pointPolicyConditionAmount = 500;

        // When
        CreatePointPolicyRequest createRequest = CreatePointPolicyRequest.builder()
                .memberId(memberId)
                .pointPolicyName(pointPolicyName)
                .pointPolicyApply(pointPolicyApply)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyConditionAmount(pointPolicyConditionAmount)
                .build();
        PointPolicy pointPolicy = createRequest.toEntity(member);

        // Then
        assertNotNull(pointPolicy);
        // pointPolicyId는 null이어도 테스트를 통과해야 함 (자동 생성되므로)
        assertNull(pointPolicy.getPointPolicyId());

        // Verify the pointPolicyName
        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName());

        // pointPolicyApplyAmount는 pointPolicyApplyType이 true일 때 설정되어야 함
        if (pointPolicyApplyType) {
            assertNotNull(pointPolicy.getPointPolicyApplyAmount());
            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyApplyAmount());
        } else {
            assertEquals(0, pointPolicy.getPointPolicyApplyAmount()); // 0으로 검증
        }

        // pointPolicyRate는 pointPolicyApplyType이 false일 때 설정되어야 함
        if (!pointPolicyApplyType) {
            assertNotNull(pointPolicy.getPointPolicyRate());
            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyRate());
        } else {
            assertEquals(0, pointPolicy.getPointPolicyRate()); // 0으로 검증
        }

        // pointPolicyCondition과 pointPolicyConditionAmount 검증
        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition());
        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount());

        // pointPolicyApplyType은 true여야 함
        assertTrue(pointPolicy.isPointPolicyApplyType());

        // pointPolicyState는 true로 설정됨
        assertTrue(pointPolicy.isPointPolicyState());
    }

    // PointPolicyRequest에 대한 테스트
    @Test
    public void testPointPolicyRequestToEntity() {
        // given
        String pointPolicyName = "Test Policy 2";
        int pointPolicyApply = 2000;
        String pointPolicyCondition = "조건2";
        boolean pointPolicyApplyType = false;  // Apply Rate
        int pointPolicyConditionAmount = 1000;

        // when
        PointPolicyRequest pointRequest = PointPolicyRequest.builder()
                .pointPolicyName(pointPolicyName)
                .pointPolicyApply(pointPolicyApply)
                .pointPolicyCondition(pointPolicyCondition)
                .pointPolicyApplyType(pointPolicyApplyType)
                .pointPolicyConditionAmount(pointPolicyConditionAmount)
                .build();

        PointPolicy pointPolicy = pointRequest.toEntity();

        // then
        assertNotNull(pointPolicy);
        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName());

        // pointPolicyApplyAmount는 null이어야 함 (Apply Rate)
        if (pointPolicyApplyType) {
            assertNotNull(pointPolicy.getPointPolicyApplyAmount());
            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyApplyAmount());
        } else {
            assertEquals(0, pointPolicy.getPointPolicyApplyAmount()); // 0으로 검증
        }

        // pointPolicyRate는 pointPolicyApply 값이어야 함 (Apply Rate)
        if (!pointPolicyApplyType) {
            assertNotNull(pointPolicy.getPointPolicyRate());
            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyRate());
        } else {
            assertEquals(0, pointPolicy.getPointPolicyRate()); // 0으로 검증
        }

        // 검증
        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition());
        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount());

        // pointPolicyApplyType은 false여야 함
        assertFalse(pointPolicy.isPointPolicyApplyType());

        // pointPolicyState는 true로 설정됨
        assertTrue(pointPolicy.isPointPolicyState());
    }
}
