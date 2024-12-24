package com.nhnacademy.taskapi.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyResponse;
import com.nhnacademy.taskapi.coupon.service.PolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PolicyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PolicyControllerTest {

    @MockBean
    private PolicyService policyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    private CreatePricePolicyRequest createPricePolicyRequest;
    private CreateRatePolicyRequest createRatePolicyRequest;
    private CreatePricePolicyResponse createPricePolicyResponse;
    private CreateRatePolicyResponse createRatePolicyResponse;
    
    @BeforeEach
    void setUp() throws NoSuchFieldException {
        
        // 테스트용 createPricePolicyRequest
        createPricePolicyRequest = new CreatePricePolicyRequest();
        
        for(Field field : createPricePolicyRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }

        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("name"), createPricePolicyRequest, "테스트정액정책1");
        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("minimumOrderAmount"), createPricePolicyRequest, 10000);
        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("discountAmount"), createPricePolicyRequest, 1000);

        // 테스트용 createPricePolicyResponse

        createPricePolicyResponse =
                new CreatePricePolicyResponse("테스트정액정책1",10000,1000);

        // 테스트용 createRatePolicyRequest
        createRatePolicyRequest = new CreateRatePolicyRequest();

        for(Field field : createRatePolicyRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }

        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("name"), createRatePolicyRequest,"테스트정률정책1");
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("minimumOrderAmount"), createRatePolicyRequest,10000);
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("discountRate"), createRatePolicyRequest,10);
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("maximumDiscountAmount"), createRatePolicyRequest,1000);

        // 테스트용 createRatePolicyResponse
        createRatePolicyResponse =
                new CreateRatePolicyResponse("테스트정률정책1",10000,10,1000);

    }

    @Test
    @Order(1)
    @DisplayName("POST task/policy/price-policy로 요청이 들어오면 - 정상적인 요청일때 - 200 OK로 응답되고, Body에는 request에 넣어준 정보가 그대로 담겨있다")
    public void createPricePolicyTest() throws Exception {

        Mockito.when(policyService.createPricePolicy(Mockito.any(CreatePricePolicyRequest.class)))
                .thenReturn(createPricePolicyResponse);

        String url = "http://localhost:8080/task/policy/price-policy";

        String reqBody = objectMapper.writeValueAsString(createPricePolicyRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(reqBody))
                .andExpect(jsonPath("$.name").value("테스트정액정책1"))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.discountAmount").value(1000));
    }

    @Test
    @Order(2)
    @DisplayName("POST task/policy/rate-policy로 요청이 들어오면 - 정상적인 요청일때 - 200 OK로 응답되고, Body에는 request에 넣어준 정보가 그대로 담겨있다")
    public void createRatePolicyTest() throws Exception {

        Mockito.when(policyService.createRatePolicy(Mockito.any(CreateRatePolicyRequest.class)))
                .thenReturn(createRatePolicyResponse);

        String url = "http://localhost:8080/task/policy/rate-policy";

        String reqBody = objectMapper.writeValueAsString(createRatePolicyRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(reqBody))
                .andExpect(jsonPath("$.name").value("테스트정률정책1"))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.discountRate").value(10))
                .andExpect(jsonPath("$.maximumDiscountAmount").value(1000));


    }
}