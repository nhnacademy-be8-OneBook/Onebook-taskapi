package com.nhnacademy.taskapi.pointTest.controller;

import com.nhnacademy.taskapi.point.controller.PointLogController;
import com.nhnacademy.taskapi.point.response.PointLogResponse;
import com.nhnacademy.taskapi.point.service.PointLogService;
import com.nhnacademy.taskapi.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PointLogControllerTest {

    @Mock
    private PointLogService pointLogService; // PointLogService를 Mock 객체로 주입

    @InjectMocks
    private PointLogController pointLogController; // PointLogController 객체에 Mock을 주입

    private Member member;

    @BeforeEach
    void setUp() {
        // Member 객체를 생성 (여기서는 id만 직접 설정해야 함)
        member = Member.createNewMember(null, "홍길동", "loginId", "password", null, Member.Gender.M, "test@example.com", "010-1234-5678", null);
        // id를 setter 없이 직접 설정해야 함
        member.modifyMember("홍길동", "password", "test@example.com", "010-1234-5678"); // 실제로 회원 생성 시 id는 자동 생성됨
    }

    @Test
    void testGetPointLogs() {
        // Pageable 객체 준비
        PageRequest pageable = PageRequest.of(0, 10);

        // PointLogResponse 리스트 생성 (Mock 데이터)
        PointLogResponse pointLogResponse1 = PointLogResponse.builder()
                .pointCurrent(100)
                .pointLogUpdatedType("증가")
                .pointLogAmount(50)
                .pointLogUpdatedAt(null)
                .build();

        PointLogResponse pointLogResponse2 = PointLogResponse.builder()
                .pointCurrent(150)
                .pointLogUpdatedType("감소")
                .pointLogAmount(-30)
                .pointLogUpdatedAt(null)
                .build();

        Page<PointLogResponse> pointLogResponses = new PageImpl<>(List.of(pointLogResponse1, pointLogResponse2));

        // pointLogService.findAllPointLogsByMemberId()가 호출되었을 때 Mock 데이터 반환
        when(pointLogService.findAllPointLogsByMemberId(member.getId(), pageable)).thenReturn(pointLogResponses);

        // getPointLogs() 메서드 실행
        ResponseEntity<Page<PointLogResponse>> response = pointLogController.getPointLogs(pageable, member);

        // 검증: ResponseEntity의 상태 코드가 OK (200)이어야 하고
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 검증: 반환된 페이지의 내용 확인
        Page<PointLogResponse> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.getContent().size()); // Mock 데이터 2개가 반환되어야 한다.

        // 검증: 첫 번째 항목이 예상한 내용인지 확인
        PointLogResponse firstLog = result.getContent().get(0);
        assertEquals(100, firstLog.pointCurrent());
        assertEquals("증가", firstLog.pointLogUpdatedType());
        assertEquals(50, firstLog.pointLogAmount());
    }
}
