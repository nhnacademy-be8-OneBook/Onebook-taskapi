package com.nhnacademy.taskapi.pointTest.repository;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.repository.PointPolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PointPolicyRepositoryTest {

    @Mock
    private PointPolicyRepository pointPolicyRepository;  // PointPolicyRepository를 모킹합니다.

    @Mock
    private Point point;

    @Mock
    private Role role;

    @Mock
    private Grade grade;

    private PointPolicy pointPolicy;
    private Member member;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Member 객체 수동 생성 (builder를 사용하지 않고 생성자 호출)
        member = new Member();

        // Point 객체 생성
        point = new Point(1000, member);

        // PointPolicy 객체 생성
        pointPolicy = new PointPolicy(
                1L,  // PointPolicy ID
                "Policy Name",  // 정책명
                10,  // 정책 비율
                100,  // 조건 금액
                "Conditions",  // 조건
                200,  // 적용 금액
                LocalDateTime.now(),  // 생성 일자
                LocalDateTime.now(),  // 수정 일자
                true,  // 적용 유형
                true,  // 상태
                point  // 연관된 포인트
        );
    }

    @Test
    public void testFindByPointPolicyName() {
        // Mocking repository behavior
        when(pointPolicyRepository.findByPointPolicyName("Policy Name")).thenReturn(pointPolicy);

        // Method call
        PointPolicy foundPolicy = pointPolicyRepository.findByPointPolicyName("Policy Name");

        // Verifying the behavior
        verify(pointPolicyRepository, times(1)).findByPointPolicyName("Policy Name");
        assert(foundPolicy != null);
        assert(foundPolicy.getPointPolicyName().equals("Policy Name"));
    }

    @Test
    public void testFindAllByPoint() {
        // Mocking repository behavior
        when(pointPolicyRepository.findAllByPoint(point)).thenReturn(List.of(pointPolicy));

        // Method call
        List<PointPolicy> pointPolicies = pointPolicyRepository.findAllByPoint(point);

        // Verifying the behavior
        verify(pointPolicyRepository, times(1)).findAllByPoint(point);
        assert(pointPolicies != null && !pointPolicies.isEmpty());
        assert(pointPolicies.get(0).getPointPolicyName().equals("Policy Name"));
    }
}