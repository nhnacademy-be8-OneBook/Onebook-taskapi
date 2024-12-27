package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointPolicyTest {

    @Test
    void testCreatePointPolicyRequest() {
        // Member 객체 생성 (테스트용)
        Member member = new Member();
        member.setId(1L);  // Member 객체의 ID 설정 (실제 ID 값 사용)

        // CreatePointPolicyRequest 객체 생성
        CreatePointPolicyRequest request = CreatePointPolicyRequest.builder()
                .memberId(member.getId())  // memberId를 전달 (Member 객체의 ID)
                .pointPolicyName("New Policy")
                .pointPolicyApply(500)
                .pointPolicyCondition("Condition")
                .pointPolicyApplyType(true)  // 적립 금액을 적용하는 타입
                .pointPolicyConditionAmount(1000)
                .build();

        // toEntity 메서드를 통해 PointPolicy 엔티티 객체 생성
        PointPolicy pointPolicy = request.toEntity(member);  // Member 객체 전달

        // 생성된 PointPolicy 객체의 필드들이 올바르게 설정되었는지 확인
        assertNotNull(pointPolicy);
        assertEquals("New Policy", pointPolicy.getPointPolicyName());
        assertEquals(1000, pointPolicy.getPointPolicyConditionAmount());
        assertTrue(pointPolicy.isActive());  // 기본값이 true로 설정되어야 함
        assertEquals(member, pointPolicy.getMember());  // Member 객체가 제대로 설정되어야 함
    }
}