package com.nhnacademy.taskapi.pointTest.response;

import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PointResponseTest {

    private PointPolicy pointPolicy;
    private CreatePointPolicyRequest createRequest;
    private PointPolicyRequest updateRequest;

    @BeforeEach
    public void setUp() {
        // PointPolicy 엔티티 생성
        pointPolicy = PointPolicy.builder()
                .pointPolicyId(1L)
                .pointPolicyName("Initial Policy")
                .pointPolicyConditionAmount(500)
                .pointPolicyRate(10)
                .pointPolicyApplyAmount(1000)
                .pointPolicyCondition("Test Condition")
                .pointPolicyApplyType(true)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyUpdatedAt(LocalDate.now())
                .pointPolicyState(true)
                .memberId(123L)
                .build();

        // CreatePointPolicyRequest 생성
        createRequest = CreatePointPolicyRequest.builder()
                .memberId(123L)
                .pointPolicyName("Test Create Policy")
                .pointPolicyApply(1000)
                .pointPolicyCondition("Test Create Condition")
                .pointPolicyApplyType(true)  // Apply Amount
                .pointPolicyConditionAmount(500)
                .build();

        // PointPolicyRequest 생성
        updateRequest = PointPolicyRequest.builder()
                .pointPolicyName("Test Update Policy")
                .pointPolicyApply(2000)
                .pointPolicyCondition("Test Update Condition")
                .pointPolicyApplyType(false)  // Apply Rate
                .pointPolicyConditionAmount(1000)
                .build();
    }

    // create 메서드 테스트
    @Test
    public void testCreatePointPolicyResponse() {
        // given
        PointPolicyResponse pointPolicyResponse = PointPolicyResponse.create(pointPolicy, createRequest);

        // then
        assertNotNull(pointPolicyResponse);
        assertEquals(String.valueOf(pointPolicy.getPointPolicyId()), pointPolicyResponse.pointPolicyId());
        assertEquals(createRequest.pointPolicyName(), pointPolicyResponse.pointPolicyName());
        assertEquals(createRequest.pointPolicyApply(), pointPolicyResponse.pointPolicyApply());
        assertEquals(createRequest.pointPolicyCondition(), pointPolicyResponse.pointPolicyCondition());
        assertTrue(pointPolicyResponse.pointPolicyApplyType());
        assertEquals(createRequest.pointPolicyConditionAmount(), pointPolicyResponse.pointPolicyConditionAmount());
        assertTrue(pointPolicyResponse.pointPolicyState());
    }

    // update 메서드 테스트
    @Test
    public void testUpdatePointPolicyResponse() {
        // given
        PointPolicyResponse pointPolicyResponse = PointPolicyResponse.update(pointPolicy, updateRequest);

        // then
        assertNotNull(pointPolicyResponse);
        assertEquals(String.valueOf(pointPolicy.getPointPolicyId()), pointPolicyResponse.pointPolicyId());
        assertEquals(updateRequest.pointPolicyName(), pointPolicyResponse.pointPolicyName());
        assertEquals(updateRequest.pointPolicyApply(), pointPolicyResponse.pointPolicyApply());
        assertEquals(updateRequest.pointPolicyCondition(), pointPolicyResponse.pointPolicyCondition());
        assertFalse(pointPolicyResponse.pointPolicyApplyType());
        assertEquals(updateRequest.pointPolicyConditionAmount(), pointPolicyResponse.pointPolicyConditionAmount());
    }

    // find 메서드 테스트
    @Test
    public void testFindPointPolicyResponse() {
        // given
        PointPolicyResponse pointPolicyResponse = PointPolicyResponse.find(pointPolicy);

        // then
        assertNotNull(pointPolicyResponse);
        assertEquals(String.valueOf(pointPolicy.getPointPolicyId()), pointPolicyResponse.pointPolicyId());
        assertEquals(pointPolicy.getPointPolicyName(), pointPolicyResponse.pointPolicyName());
        assertEquals(pointPolicy.isPointPolicyApplyType() ? pointPolicy.getPointPolicyApplyAmount() : pointPolicy.getPointPolicyRate(), pointPolicyResponse.pointPolicyApply());
        assertEquals(pointPolicy.getPointPolicyCondition(), pointPolicyResponse.pointPolicyCondition());
        assertEquals(pointPolicy.getPointPolicyConditionAmount(), pointPolicyResponse.pointPolicyConditionAmount());
        assertEquals(pointPolicy.getPointPolicyCreatedAt(), pointPolicyResponse.pointPolicyCreatedAt());
        assertEquals(pointPolicy.getPointPolicyUpdatedAt() != null ? pointPolicy.getPointPolicyUpdatedAt().toString() : null, pointPolicyResponse.pointPolicyUpdatedAt());
        assertTrue(pointPolicyResponse.pointPolicyState());
    }
}
