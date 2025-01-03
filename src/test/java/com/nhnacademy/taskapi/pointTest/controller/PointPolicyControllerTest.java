package com.nhnacademy.taskapi.pointTest.controller;

import com.nhnacademy.taskapi.point.controller.PointPolicyController;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.request.PointPolicyRequest;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import com.nhnacademy.taskapi.point.domain.PointPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations; // 추가된 import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PointPolicyControllerTest {

    @Mock
    private PointPolicyService pointPolicyService;

    @InjectMocks
    private PointPolicyController pointPolicyController;

    private CreatePointPolicyRequest createRequest;
    private PointPolicyResponse pointPolicyResponse;
    private PointPolicy pointPolicyEntity;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this); // mock 객체 초기화 추가

        // 테스트에 필요한 데이터 초기화
        createRequest = CreatePointPolicyRequest.builder()
                .pointPolicyName("POLICY_NAME")
                .pointPolicyRate(10)
                .pointPolicyConditionAmount(1000)
                .pointPolicyApplyAmount(500)
                .pointPolicyCondition("구매조건")
                .pointPolicyApplyType(true)
                .build();

        pointPolicyEntity = PointPolicy.builder()
                .pointPolicyName("POLICY_NAME")
                .pointPolicyRate(10)
                .pointPolicyConditionAmount(1000)
                .pointPolicyApplyAmount(500)
                .pointPolicyCondition("구매조건")
                .pointPolicyApplyType(true)
                .pointPolicyCreatedAt(LocalDateTime.now())
                .pointPolicyState(true)
                .build();

        pointPolicyResponse = PointPolicyResponse.builder()
                .pointPolicyId(1L)
                .pointPolicyName("POLICY_NAME")
                .pointPolicyConditionAmount(1000)
                .pointPolicyRate(10)
                .pointPolicyApplyAmount(500)
                .pointPolicyCondition("구매조건")
                .pointPolicyApplyType(true)
                .pointPolicyCreatedAt(LocalDateTime.now())
                .pointPolicyUpdatedAt(LocalDateTime.now())
                .pointPolicyState(true)
                .build();
    }

    @Test
    void testCreatePointPolicy() {
        // pointPolicyService.createPointPolicy 메서드가 호출될 때 반환할 mock 응답을 설정
        when(pointPolicyService.createPointPolicy(createRequest)).thenReturn(pointPolicyResponse);

        // PointPolicyController의 createPointPolicy 메서드 호출
        ResponseEntity<PointPolicyResponse> response = pointPolicyController.createPointPolicy(createRequest);

        // 응답 코드와 본문 검증
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("POLICY_NAME", response.getBody().pointPolicyName());
        assertEquals(10, response.getBody().pointPolicyRate());
        assertEquals(1000, response.getBody().pointPolicyConditionAmount());
        assertEquals(500, response.getBody().pointPolicyApplyAmount());
        assertEquals("구매조건", response.getBody().pointPolicyCondition());
        assertTrue(response.getBody().pointPolicyApplyType());
    }

    @Test
    void testFindPointPolicyById() {
        Long pointPolicyId = 1L;

        // pointPolicyService.findPointPolicyById 메서드가 호출될 때 반환할 mock 응답 설정
        when(pointPolicyService.findPointPolicyById("1")).thenReturn(pointPolicyResponse);

        // PointPolicyController의 findPointPolicyById 메서드 호출
        ResponseEntity<PointPolicyResponse> response = pointPolicyController.findPointPolicyById(pointPolicyId);

        // 응답 코드와 본문 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("POLICY_NAME", response.getBody().pointPolicyName());
    }

    @Test
    void testFindAllPointPolicies() {
        // Pageable mock 객체 생성
        Pageable pageable = mock(Pageable.class);

        // Page<PointPolicyResponse>를 반환하도록 설정
        Page<PointPolicyResponse> pageResponse = mock(Page.class);
        when(pageResponse.getContent()).thenReturn(List.of(pointPolicyResponse)); // getContent() 메서드 사용
        when(pointPolicyService.findAllPointPolicies(pageable)).thenReturn(pageResponse);

        // PointPolicyController의 findAllPointPolicies 메서드 호출
        ResponseEntity<Page<PointPolicyResponse>> response = pointPolicyController.findAllPointPolicies(pageable);

        // 응답 코드와 본문 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());  // .getContent()로 List를 받아 size() 호출
        assertEquals("POLICY_NAME", response.getBody().getContent().get(0).pointPolicyName());
    }

    @Test
    void testUpdatePointPolicy() {
        Long pointPolicyId = 1L;

        // 업데이트용 PointPolicyRequest
        PointPolicyRequest updateRequest = PointPolicyRequest.builder()
                .pointPolicyName("UPDATED_POLICY_NAME")
                .pointPolicyRate(20)
                .pointPolicyConditionAmount(2000)
                .pointPolicyApplyAmount(1000)
                .pointPolicyCondition("새로운 조건")
                .pointPolicyApplyType(false)
                .build();

        // pointPolicyService.updatePointPolicyById 메서드 호출 시 반환할 mock 응답 설정
        when(pointPolicyService.updatePointPolicyById("1", updateRequest)).thenReturn(pointPolicyResponse);

        // PointPolicyController의 updatePointPolicy 메서드 호출
        ResponseEntity<PointPolicyResponse> response = pointPolicyController.updatePointPolicy(pointPolicyId, updateRequest);

        // 응답 코드와 본문 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("POLICY_NAME", response.getBody().pointPolicyName());  // 여기는 mock이므로 실제 내용은 mock과 같음
    }

    @Test
    void testDeletePointPolicy() {
        Long pointPolicyId = 1L;

        // pointPolicyService.deletePointPolicyById 메서드가 정상적으로 호출되는지 확인
        doNothing().when(pointPolicyService).deletePointPolicyById("1");

        // PointPolicyController의 deletePointPolicy 메서드 호출
        ResponseEntity<Void> response = pointPolicyController.deletePointPolicy(pointPolicyId);

        // 응답 코드 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pointPolicyService, times(1)).deletePointPolicyById("1");
    }
}
