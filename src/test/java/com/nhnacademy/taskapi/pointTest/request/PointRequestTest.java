package com.nhnacademy.taskapi.pointTest.request;//package com.nhnacademy.taskapi.pointTest.request;
//
//import com.nhnacademy.taskapi.point.domain.PointPolicy;
//import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
//import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class PointRequestTest {
//
//    // CreatePointPolicyRequest에 대한 테스트
//    @Test
//    public void testCreatePointPolicyRequestToEntity() {
//        // given
//        Long memberId = 1L;
//        String pointPolicyName = "Test Policy";
//        int pointPolicyApply = 1000;
//        String pointPolicyCondition = "조건1";
//        boolean pointPolicyApplyType = true;  // Apply Amount
//        int pointPolicyConditionAmount = 500;
//
//        // when
//        CreatePointPolicyRequest createRequest = CreatePointPolicyRequest.builder()
//                .memberId(memberId)
//                .pointPolicyName(pointPolicyName)
//                .pointPolicyApply(pointPolicyApply)
//                .pointPolicyCondition(pointPolicyCondition)
//                .pointPolicyApplyType(pointPolicyApplyType)
//                .pointPolicyConditionAmount(pointPolicyConditionAmount)
//                .build();
//
//        PointPolicy pointPolicy = createRequest.toEntity();
//
//        // then
//        assertNotNull(pointPolicy);
//        assertEquals(memberId, pointPolicy.getMemberId());
//        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName());
//
//        // pointPolicyApplyAmount는 true일 때만 값이 있어야 함
//        if (pointPolicyApplyType) {
//            assertNotNull(pointPolicy.getPointPolicyApplyAmount());
//            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyApplyAmount());
//        } else {
//            assertEquals(0, pointPolicy.getPointPolicyApplyAmount()); // 0으로 검증
//        }
//
//        // pointPolicyRate는 null이어야 함
//        if (!pointPolicyApplyType) {
//            assertNotNull(pointPolicy.getPointPolicyRate());
//            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyRate());
//        } else {
//            assertEquals(0, pointPolicy.getPointPolicyRate()); // 0으로 검증
//        }
//
//        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition());
//        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount());
//        assertTrue(pointPolicy.isPointPolicyApplyType());
//        assertTrue(pointPolicy.isPointPolicyState());  // pointPolicyState는 true로 설정됨
//    }
//
//    // PointPolicyRequest에 대한 테스트
//    @Test
//    public void testPointPolicyRequestToEntity() {
//        // given
//        String pointPolicyName = "Test Policy 2";
//        int pointPolicyApply = 2000;
//        String pointPolicyCondition = "조건2";
//        boolean pointPolicyApplyType = false;  // Apply Rate
//        int pointPolicyConditionAmount = 1000;
//
//        // when
//        PointPolicyRequest pointRequest = PointPolicyRequest.builder()
//                .pointPolicyName(pointPolicyName)
//                .pointPolicyApply(pointPolicyApply)
//                .pointPolicyCondition(pointPolicyCondition)
//                .pointPolicyApplyType(pointPolicyApplyType)
//                .pointPolicyConditionAmount(pointPolicyConditionAmount)
//                .build();
//
//        PointPolicy pointPolicy = pointRequest.toEntity();
//
//        // then
//        assertNotNull(pointPolicy);
//        assertEquals(pointPolicyName, pointPolicy.getPointPolicyName());
//
//        // pointPolicyApplyAmount는 null이어야 함 (Apply Rate)
//        if (pointPolicyApplyType) {
//            assertNotNull(pointPolicy.getPointPolicyApplyAmount());
//            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyApplyAmount());
//        } else {
//            assertEquals(0, pointPolicy.getPointPolicyApplyAmount()); // 0으로 검증
//        }
//
//        // pointPolicyRate는 pointPolicyApply 값이어야 함
//        if (!pointPolicyApplyType) {
//            assertNotNull(pointPolicy.getPointPolicyRate());
//            assertEquals(pointPolicyApply, pointPolicy.getPointPolicyRate());
//        } else {
//            assertEquals(0, pointPolicy.getPointPolicyRate()); // 0으로 검증
//        }
//
//        assertEquals(pointPolicyCondition, pointPolicy.getPointPolicyCondition());
//        assertEquals(pointPolicyConditionAmount, pointPolicy.getPointPolicyConditionAmount());
//        assertFalse(pointPolicy.isPointPolicyApplyType());
//        assertTrue(pointPolicy.isPointPolicyState());  // pointPolicyState는 true로 설정됨
//    }
//}