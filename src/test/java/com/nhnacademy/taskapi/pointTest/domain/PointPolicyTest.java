package com.nhnacademy.taskapi.pointTest.domain;

import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.roles.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PointPolicyTest {

    @Test
    void testCreatePointPolicyRequest() {
        // Grade 객체 생성 (예시: "Regular" 등급)
        Grade grade = Grade.create("Regular", 10, "Regular grade description");

        // Role 객체 생성 (예시: "MEMBER" 역할)
        Role role = Role.createRole("MEMBER", "Standard user role");

        // Member 객체 생성 (id는 자동으로 설정되도록 두고, 나머지 필드만 설정)
        Member member = Member.createNewMember(
                grade, // 적절한 Grade 객체
                "John Doe",  // 이름
                "johndoe123",  // 로그인 아이디
                "password123",  // 비밀번호
                LocalDate.of(1990, 1, 1),  // 생일
                Member.Gender.M,  // 성별
                "johndoe@example.com",  // 이메일
                "010-1234-5678",  // 전화번호
                role  // 적절한 Role 객체
        );

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
