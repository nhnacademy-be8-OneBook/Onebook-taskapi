// 테스트 코드 수정중
//package com.nhnacademy.taskapi.pointTest;
//
//import com.nhnacademy.taskapi.point.domain.Point;
//import com.nhnacademy.taskapi.point.domain.PointLog;
//import com.nhnacademy.taskapi.point.domain.PointPolicy;
//import com.nhnacademy.taskapi.point.exception.PointPolicyException;
//import com.nhnacademy.taskapi.point.jpa.JpaPointRepository;
//import com.nhnacademy.taskapi.point.repository.PointLogRepository;
//import com.nhnacademy.taskapi.point.jpa.JpaPointPolicyRepository;
//import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
//import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
//import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
//import com.nhnacademy.taskapi.point.service.impl.PointPolicyServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class PointPolicyServiceImplTest {
//
//    @Mock
//    private JpaPointPolicyRepository pointPolicyRepository;
//
//    @Mock
//    private JpaPointRepository pointRepository;
//
//    @Mock
//    private PointLogRepository pointLogRepository;
//
//    @InjectMocks
//    private PointPolicyServiceImpl pointPolicyService;
//
//    private PointPolicy pointPolicy;
//    private CreatePointPolicyRequest createPointPolicyRequest;
//    private Point point;
//    private PointLog pointLog;
//
//    @BeforeEach
//    void setUp() {
//        pointPolicy = new PointPolicy(1L, "Test Policy", 10, 0, "Condition Test", 100, LocalDate.now(), LocalDate.now(), true, true, 1L);
//        createPointPolicyRequest = new CreatePointPolicyRequest(1L, "Test Policy", 100, "Condition Test", true, 0);
//        point = new Point(1L, 1000); // Corrected constructor
//        pointLog = new PointLog(1L, point, "POLICY_CREATE", 0, LocalDate.now(), LocalDate.now()); // Corrected constructor
//    }
//
//    @Test
//    void createPointPolicyTest() {
//        when(pointRepository.findByMember_MemberId(createPointPolicyRequest.memberId())).thenReturn(Optional.of(point));
//        when(pointPolicyRepository.save(any(PointPolicy.class))).thenReturn(pointPolicy);
//
//        PointPolicyResponse response = pointPolicyService.createPointPolicy(createPointPolicyRequest);
//
//        assertNotNull(response);
//        assertEquals("Test Policy", response.getPointPolicyName());
//        verify(pointPolicyRepository, times(1)).save(any(PointPolicy.class));
//        verify(pointLogRepository, times(1)).save(any(PointLog.class));
//    }
//
//    @Test
//    void findPointPolicyByIdTest() {
//        when(pointPolicyRepository.findById(1L)).thenReturn(Optional.of(pointPolicy));
//
//        PointPolicyResponse response = pointPolicyService.findPointPolicyById(1L);
//
//        assertNotNull(response);
//        assertEquals("Test Policy", response.getPointPolicyName());
//    }
//
//    @Test
//    void updatePointPolicyTest() {
//        PointPolicyRequest updateRequest = new PointPolicyRequest("Updated Policy", 150, "Updated Condition", true, 0, 0);
//        when(pointPolicyRepository.findById(1L)).thenReturn(Optional.of(pointPolicy));
//        when(pointPolicyRepository.save(any(PointPolicy.class))).thenReturn(pointPolicy);
//
//        PointPolicyResponse response = pointPolicyService.updatePointPolicyById(1L, updateRequest);
//
//        assertNotNull(response);
//        assertEquals("Updated Policy", response.getPointPolicyName());
//        verify(pointPolicyRepository, times(1)).save(any(PointPolicy.class));
//        verify(pointLogRepository, times(1)).save(any(PointLog.class));
//    }
//
//    @Test
//    void deletePointPolicyTest() {
//        when(pointPolicyRepository.findById(1L)).thenReturn(Optional.of(pointPolicy));
//        when(pointRepository.findByMember_MemberId(1L)).thenReturn(Optional.of(point));
//
//        pointPolicyService.deletePointPolicyById(1L);
//
//        verify(pointPolicyRepository, times(1)).save(any(PointPolicy.class));
//        verify(pointLogRepository, times(1)).save(any(PointLog.class));
//    }
//
//    @Test
//    void findPointPolicyByIdNotFoundTest() {
//        when(pointPolicyRepository.findById(1L)).thenReturn(Optional.empty());
//
//        PointPolicyException exception = assertThrows(PointPolicyException.class, () -> pointPolicyService.findPointPolicyById(1L));
//        assertEquals("포인트 정책을 찾을 수 없습니다.", exception.getMessage());
//    }
//
//    @Test
//    void createPointPolicyMemberNotFoundTest() {
//        when(pointRepository.findByMember_MemberId(createPointPolicyRequest.memberId())).thenReturn(Optional.empty());
//
//        PointPolicyException exception = assertThrows(PointPolicyException.class, () -> pointPolicyService.createPointPolicy(createPointPolicyRequest));
//        assertEquals("사용자 포인트를 찾을 수 없습니다.", exception.getMessage());
//    }
//}
