package com.nhnacademy.taskapi.pointTest.jpa;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.jpa.JpaPointLogRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.roles.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PointJpaTest {

    @Mock
    private JpaPointLogRepository pointLogRepository;

    @Mock
    private JpaPointPolicyRepository pointPolicyRepository;

    @Mock
    private JpaPointRepository pointRepository;

    @InjectMocks
    private PointJpaTest testClass;
    private Long memberId;
    private Point point;
    private PointLog pointLog;
    private PointPolicy pointPolicy;
    private Member member;  // Member 객체 추가
    private Grade grade;  // Grade 객체 추가
    private Role role;  // Role 객체 추가

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Grade 객체 생성 (예시: "Regular" 등급)
        grade = Grade.create("Regular", 10, "Regular grade description");

        // Role 객체 생성 (예시: "MEMBER" 역할)
        role = Role.createRole("MEMBER", "Standard user role");

        // Member 객체 생성
        member = Member.createNewMember(
                grade,  // 적절한 Grade 객체
                "John Doe",  // 이름
                "johndoe123",  // 로그인 아이디
                "password123",  // 비밀번호
                LocalDate.of(1990, 1, 1),  // 생일
                Member.Gender.M,  // 성별
                "johndoe@example.com",  // 이메일
                "010-1234-5678",  // 전화번호
                role  // 적절한 Role 객체
        );

        point = new Point(1000, null);  // 임시 객체
        point.setAmount(1000);
        pointLog = new PointLog(1L, LocalDateTime.now(), "ADD", 100, point);

        // Member 객체를 넣어서 PointPolicy 객체 초기화
        pointPolicy = PointPolicy.builder()
                .pointPolicyId(1L)
                .pointPolicyName("Test Policy")
                .value("100")
                .description("Condition")
                .pointPolicyRate(10)
                .pointPolicyConditionAmount(100)
                .pointPolicyCondition("Condition")
                .pointPolicyApplyAmount(100)
                .pointPolicyCreatedAt(LocalDate.now())  // LocalDateTime 사용
                .pointPolicyUpdatedAt(LocalDate.now())  // LocalDateTime 사용
                .pointPolicyApplyType(true)
                .pointPolicyState(true)
                .member(member)  // 실제 Member 객체 사용
                .build();
    }

    @Test
    void testFindByPointMemberIdOrderByPointLogUpdatedAtDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pointLogRepository.findByPoint_Member_IdOrderByPointLogUpdatedAtDesc(memberId, pageable))
                .thenReturn(Page.empty());

        Page<PointLog> result = pointLogRepository.findByPoint_Member_IdOrderByPointLogUpdatedAtDesc(memberId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pointLogRepository, times(1)).findByPoint_Member_IdOrderByPointLogUpdatedAtDesc(memberId, pageable);
    }

    @Test
    void testFindByPointPolicyName() {
        String policyName = "Test Policy";
        when(pointPolicyRepository.findByPointPolicyName(policyName))
                .thenReturn(pointPolicy);

        PointPolicy result = pointPolicyRepository.findByPointPolicyName(policyName);

        assertNotNull(result);
        assertEquals(policyName, result.getPointPolicyName());
        verify(pointPolicyRepository, times(1)).findByPointPolicyName(policyName);
    }

    @Test
    void testFindAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pointPolicyRepository.findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(pageable))
                .thenReturn(Page.empty());

        Page<PointPolicy> result = pointPolicyRepository.findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pointPolicyRepository, times(1)).findAllByOrderByPointPolicyCreatedAtAscPointPolicyStateDesc(pageable);
    }

    @Test
    void testFindByMemberId() {
        Point point = Point.builder()
                .pointCurrent(1000)  // 포인트 값 설정
                .member(member)      // member 연결
                .build();

        point = Mockito.spy(point);
        doReturn(1L).when(point).getPointId();  // pointId를 1L로 설정

        when(pointRepository.findByMember_Id(1L)).thenReturn(Optional.of(point));

        Optional<Point> result = pointRepository.findByMember_Id(1L);

        assertTrue(result.isPresent(), "Expected point to be present");
        assertNotNull(result.get().getPointId(), "Expected pointId to be non-null");
        assertEquals(1000, result.get().getAmount(), "Expected pointCurrent to be 1000");
        verify(pointRepository, times(1)).findByMember_Id(1L);
    }
}
