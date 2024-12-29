package com.nhnacademy.taskapi.coupon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.service.policies.PolicyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(PolicyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PolicyControllerTest {

    @MockBean
    private PolicyService policyService;

    private int port = 8080;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static AddRatePolicyForBookRequest addRatePolicyForBookRequest;
    private static AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest;
    private static AddRatePolicyForBookResponse addRatePolicyForBookResponse;
    private static AddRatePolicyForCategoryResponse addRatePolicyForCategoryResponse;

    @BeforeAll
    static void setUp() throws NoSuchFieldException {

        // 정률정책 for Book 추가 요청
        addRatePolicyForBookRequest = new AddRatePolicyForBookRequest();
        for(Field field : addRatePolicyForBookRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForBookRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForBookRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForBookRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForBookRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForBookRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("bookId")
                ,addRatePolicyForBookRequest
                ,1L);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForBookRequest
                ,0);

        // 정률정책 for Book 추가 응답
        addRatePolicyForBookResponse = new AddRatePolicyForBookResponse(
                10,
                30000,
                10000,
                LocalDateTime.of(2024,1,1,12,0),
                LocalDateTime.of(2024,1,10,12,0),
                "테스트용 정책",
                "테스트용 정책 설명",
                1L,
                0
        );

        // 정률정책 for category 추가 요청
        addRatePolicyForCategoryRequest = new AddRatePolicyForCategoryRequest();
        for(Field field : addRatePolicyForCategoryRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForCategoryRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForCategoryRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForCategoryRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("categoryId")
                ,addRatePolicyForCategoryRequest
                ,0);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForCategoryRequest
                ,0);

        // 정률정책 for Category 추가 응답
        addRatePolicyForCategoryResponse = new AddRatePolicyForCategoryResponse(
                10,
                30000,
                10000,
                LocalDateTime.of(2024,1,1,12,0),
                LocalDateTime.of(2024,1,10,12,0),
                "테스트용 정책",
                "테스트용 정책 설명",
                0,
                0
        );
    }


    @Test
    @DisplayName("정룰정책 for Book 등록 요청시 - 200과 응답이 잘 돌아오는지 확인")
    void addRatePolicyForBookTest() throws Exception {

        Mockito.when(policyService.addRatePolicyForBook(Mockito.any(AddRatePolicyForBookRequest.class)))
                .thenReturn(addRatePolicyForBookResponse);

        String url = "http://localhost:"+port +"/task/policies/rate/book";

        String req = objectMapper.writeValueAsString(addRatePolicyForBookRequest);

        mockMvc.perform(post(url).header("X-MEMBER-ID",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(jsonPath("$.discountRate").value(10))
                .andExpect(jsonPath("$.minimumOrderAmount").value(30000))
                .andExpect(jsonPath("$.maximumDiscountPrice").value(10000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T12:00:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정책"))
                .andExpect(jsonPath("$.description").value("테스트용 정책 설명"))
                .andExpect(jsonPath("$.bookId").value(1L))
                .andExpect(jsonPath("$.policyStatusId").value(0));
    }

    @Test
    @DisplayName("정룰정책 for Category 등록 요청시 - 200과 응답이 잘 돌아오는지 확인")
    void addRatePolicyForCategoryTest() throws Exception {

        Mockito.when(policyService.addRatePolicyForCategory(Mockito.any(AddRatePolicyForCategoryRequest.class)))
                .thenReturn(addRatePolicyForCategoryResponse);

        String url = "http://localhost:"+port +"/task/policies/rate/category";

        String req = objectMapper.writeValueAsString(addRatePolicyForCategoryRequest);

        mockMvc.perform(post(url).header("X-MEMBER-ID",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(jsonPath("$.discountRate").value(10))
                .andExpect(jsonPath("$.minimumOrderAmount").value(30000))
                .andExpect(jsonPath("$.maximumDiscountPrice").value(10000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T12:00:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정책"))
                .andExpect(jsonPath("$.description").value("테스트용 정책 설명"))
                .andExpect(jsonPath("$.categoryId").value(0))
                .andExpect(jsonPath("$.policyStatusId").value(0));
    }



}