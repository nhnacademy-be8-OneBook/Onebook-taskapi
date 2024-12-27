package com.nhnacademy.taskapi.pointTest.controller;

import com.nhnacademy.taskapi.point.controller.PointPolicyController;
import com.nhnacademy.taskapi.point.response.PointPolicyResponse;
import com.nhnacademy.taskapi.point.request.CreatePointPolicyRequest;
import com.nhnacademy.taskapi.point.service.PointPolicyService;
import com.nhnacademy.taskapi.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PointPolicyService pointPolicyService;

    @InjectMocks
    private PointPolicyController pointPolicyController;

    private Member mockMember;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pointPolicyController).build();
        mockMember = new Member();
    }

    // PointPolicyController 테스트 - 포인트 정책 생성
    @Test
    public void testCreatePointPolicy() throws Exception {
        // given
        CreatePointPolicyRequest policyRequest = CreatePointPolicyRequest.builder()
                .memberId(1L)
                .pointPolicyName("Test Policy")
                .pointPolicyApply(1000)
                .pointPolicyCondition("Test Condition")
                .pointPolicyApplyType(true)
                .pointPolicyConditionAmount(500)
                .build();

        // PointPolicyResponse 수정된 값
        PointPolicyResponse policyResponse = PointPolicyResponse.builder()
                .pointPolicyId("1")
                .pointPolicyName("Test Policy")
                .pointPolicyApply(1000)  // pointPolicyApply로 수정
                .pointPolicyCondition("Test Condition")
                .pointPolicyApplyType(true)
                .pointPolicyConditionAmount(500)
                .pointPolicyCreatedAt(LocalDate.now()) // LocalDateTime으로 수정
                .pointPolicyState(true)
                .build();

        // when
        when(pointPolicyService.createPointPolicy(policyRequest)).thenReturn(policyResponse);

        // then
        mockMvc.perform(post("/member/admin/point-policies")
                        .contentType("application/json")
                        .content("{\"memberId\": 1, \"pointPolicyName\": \"Test Policy\", \"pointPolicyApply\": 1000, \"pointPolicyCondition\": \"Test Condition\", \"pointPolicyApplyType\": true, \"pointPolicyConditionAmount\": 500}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pointPolicyName").value("Test Policy"))
                .andExpect(jsonPath("$.pointPolicyApply").value(1000))  // pointPolicyApply로 수정
                .andExpect(jsonPath("$.pointPolicyCondition").value("Test Condition"));
    }

    // PointPolicyController 테스트 - 포인트 정책 조회
    @Test
    public void testFindPointPolicyById() throws Exception {
        // given
        PointPolicyResponse policyResponse = PointPolicyResponse.builder()
                .pointPolicyId("1")
                .pointPolicyName("Test Policy")
                .pointPolicyApply(1000)  // pointPolicyApply로 수정
                .pointPolicyCondition("Test Condition")
                .pointPolicyApplyType(true)
                .pointPolicyConditionAmount(500)
                .pointPolicyCreatedAt(LocalDate.now())
                .pointPolicyState(true)
                .build();

        // when
        when(pointPolicyService.findPointPolicyById("1")).thenReturn(policyResponse);

        // then
        mockMvc.perform(get("/member/admin/point-policies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pointPolicyName").value("Test Policy"))
                .andExpect(jsonPath("$.pointPolicyApply").value(1000))  // pointPolicyApply로 수정
                .andExpect(jsonPath("$.pointPolicyCondition").value("Test Condition"));
    }
}