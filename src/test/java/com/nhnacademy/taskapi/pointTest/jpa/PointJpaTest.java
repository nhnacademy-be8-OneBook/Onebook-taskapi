package com.nhnacademy.taskapi.pointTest.jpa;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.point.domain.Point;
import com.nhnacademy.taskapi.point.domain.PointLog;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import com.nhnacademy.taskapi.point.jpa.JpaPointLogRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 필요한 데이터 초기화
        memberId = 1L;

        // Point 객체 생성 (Member 객체도 연결 가능)
        point = new Point(1000, null);  // 임시 객체
        point.setAmount(1000);

        // PointLog 객체 생성
        pointLog = new PointLog(1L, LocalDateTime.now(), "ADD", 100, point);

        // PointPolicy 객체 생성
        pointPolicy = new PointPolicy(1L, "Test Policy", 10, 100, "Condition", 100, LocalDateTime.now(), LocalDateTime.now(), true, true, point);
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
        Member member = new Member();  // Member 객체를 필요에 맞게 생성
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

    @Test
    void testFindPointPolicyById() {
        Long policyId = 1L;
        when(pointPolicyRepository.findById(policyId))
                .thenReturn(Optional.of(pointPolicy));

        Optional<PointPolicy> result = pointPolicyRepository.findById(policyId);

        assertTrue(result.isPresent(), "Expected PointPolicy to be present");
        assertEquals(policyId, result.get().getPointPolicyId(), "Expected PointPolicy ID to be 1");
        assertEquals("Test Policy", result.get().getPointPolicyName(), "Expected PointPolicy Name to be 'Test Policy'");
        verify(pointPolicyRepository, times(1)).findById(policyId);
    }

    @Test
    void testPointPolicyCreation() {
        when(pointPolicyRepository.save(pointPolicy)).thenReturn(pointPolicy);

        PointPolicy savedPointPolicy = pointPolicyRepository.save(pointPolicy);

        assertNotNull(savedPointPolicy);
        assertEquals("Test Policy", savedPointPolicy.getPointPolicyName());
        assertEquals(10, savedPointPolicy.getPointPolicyRate());
        verify(pointPolicyRepository, times(1)).save(pointPolicy);
    }

    @Test
    void testPointPolicyUpdate() {
        Long policyId = 1L;

        // 기존 pointPolicy의 pointPolicyState가 false로 설정되어 있기 때문에,
        // 이를 true로 설정한 새로운 updatedPolicy 객체를 생성합니다.
        PointPolicy updatedPolicy = new PointPolicy(
                1L, "Updated Policy", 15, 200, "Updated Condition", 200, LocalDateTime.now(), LocalDateTime.now(), true, true, point
        );

        // pointPolicyRepository에서 id로 찾아온 기존 정책이 존재한다고 가정
        when(pointPolicyRepository.findById(policyId)).thenReturn(Optional.of(pointPolicy));

        // 업데이트된 pointPolicy 객체를 저장한 뒤 반환하도록 설정
        when(pointPolicyRepository.save(updatedPolicy)).thenReturn(updatedPolicy);

        // updatePolicy가 저장되는 과정
        PointPolicy result = pointPolicyRepository.save(updatedPolicy);

        // 저장된 updatedPolicy를 검증
        assertNotNull(result);
        assertEquals("Updated Policy", result.getPointPolicyName());
        assertEquals(15, result.getPointPolicyRate());
        assertTrue(result.isPointPolicyState(), "Expected pointPolicyState to be true");
        verify(pointPolicyRepository, times(1)).save(updatedPolicy);
    }

}
