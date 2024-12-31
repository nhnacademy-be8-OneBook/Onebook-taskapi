package com.nhnacademy.taskapi.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddPricePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.create.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.create.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetPricePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.read.GetRatePolicyForCategoryResponse;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private static AddPricePolicyForBookRequest addPricePolicyForBookRequest;
    private static AddPricePolicyForCategoryRequest addPricePolicyForCategoryRequest;
    private static AddPricePolicyForBookResponse addPricePolicyForBookResponse;
    private static AddPricePolicyForCategoryResponse addPricePolicyForCategoryResponse;

    @BeforeAll
    static void setUp() throws NoSuchFieldException {

        // 정률정책 for Book 등록 요청
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

        // 정률정책 for Book 등록 응답
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

        // 정률정책 for category 등록 요청
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

        // 정률정책 for Category 등록 응답
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

        // 정액정책 for Book 등록 요청
        addPricePolicyForBookRequest = new AddPricePolicyForBookRequest();
        for(Field field : addPricePolicyForBookRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addPricePolicyForBookRequest
                , 20000);
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("discountPrice")
                ,addPricePolicyForBookRequest
                , 5000);
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addPricePolicyForBookRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addPricePolicyForBookRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("name")
                ,addPricePolicyForBookRequest
                , "테스트용 정액정책 for Book");
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("description")
                ,addPricePolicyForBookRequest
                , "테스트용 정액정책 for Book 설명");
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("bookId")
                ,addPricePolicyForBookRequest
                ,1L);
        ReflectionUtils.setField(
                addPricePolicyForBookRequest.getClass().getDeclaredField("policyStatusId")
                ,addPricePolicyForBookRequest
                ,0);

        // 정액정책 for Book 등록 응답
        addPricePolicyForBookResponse = new AddPricePolicyForBookResponse(
                        20000,
                        5000,
                        LocalDateTime.of(2024,1,1,12,0),
                        LocalDateTime.of(2024,1,10,12,0),
                        "테스트용 정액정책 for Book",
                        "테스트용 정액정책 for Book 설명",
                        1L,
                        0
                );

        // 정액정책 for Category 등록 요청
        addPricePolicyForCategoryRequest = new AddPricePolicyForCategoryRequest();
        for(Field field : addPricePolicyForCategoryRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addPricePolicyForCategoryRequest
                , 20000);
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("discountPrice")
                ,addPricePolicyForCategoryRequest
                , 5000);
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addPricePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addPricePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("name")
                ,addPricePolicyForCategoryRequest
                , "테스트용 정액정책 for Category");
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("description")
                ,addPricePolicyForCategoryRequest
                , "테스트용 정액정책 for Category 설명");
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("categoryId")
                ,addPricePolicyForCategoryRequest
                ,0);
        ReflectionUtils.setField(
                addPricePolicyForCategoryRequest.getClass().getDeclaredField("policyStatusId")
                ,addPricePolicyForCategoryRequest
                ,0);

        // 정액정책 for Category 등록 응답
        addPricePolicyForCategoryResponse = new AddPricePolicyForCategoryResponse(
                        20000,
                        5000,
                        LocalDateTime.of(2024,1,1,12,0),
                        LocalDateTime.of(2024,1,10,12,0),
                        "테스트용 정액정책 for Category",
                        "테스트용 정액정책 for Category 설명",
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

    @Test
    @DisplayName("정액정책 for Book 등록 요청시 - 200과 응답이 잘 돌아오는지 확인")
    void addPricePolicyForBookTest() throws Exception {

        Mockito.when(policyService.addPricePolicyForBook(Mockito.any(AddPricePolicyForBookRequest.class)))
                .thenReturn(addPricePolicyForBookResponse);

        String url = "http://localhost:"+port +"/task/policies/price/book";

        String req = objectMapper.writeValueAsString(addPricePolicyForBookRequest);

        mockMvc.perform(post(url).header("X-MEMBER-ID",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(jsonPath("$.minimumOrderAmount").value(20000))
                .andExpect(jsonPath("$.discountPrice").value(5000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T12:00:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정액정책 for Book"))
                .andExpect(jsonPath("$.description").value("테스트용 정액정책 for Book 설명"))
                .andExpect(jsonPath("$.bookId").value(1L))
                .andExpect(jsonPath("$.policyStatusId").value(0));
    }

    @Test
    @DisplayName("정액정책 for Category 등록 요청시 - 200과 응답이 잘 돌아오는지 확인")
    void addPricePolicyForCategoryTest() throws Exception {

        Mockito.when(policyService.addPricePolicyForCategory(Mockito.any(AddPricePolicyForCategoryRequest.class)))
                .thenReturn(addPricePolicyForCategoryResponse);

        String url = "http://localhost:"+port +"/task/policies/price/category";

        String req = objectMapper.writeValueAsString(addPricePolicyForCategoryRequest);

        mockMvc.perform(post(url).header("X-MEMBER-ID",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(jsonPath("$.minimumOrderAmount").value(20000))
                .andExpect(jsonPath("$.discountPrice").value(5000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T12:00:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정액정책 for Category"))
                .andExpect(jsonPath("$.description").value("테스트용 정액정책 for Category 설명"))
                .andExpect(jsonPath("$.categoryId").value(0))
                .andExpect(jsonPath("$.policyStatusId").value(0));
    }


    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정률정책 for Book들을 - Pagenation 해서 받아오기")
    void getRatePoliciesForBookTest() throws Exception {

        List<GetRatePolicyForBookResponse> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            GetRatePolicyForBookResponse getRatePolicyForBookResponse = new GetRatePolicyForBookResponse();
            for(Field field : getRatePolicyForBookResponse.getClass().getDeclaredFields()){
                field.setAccessible(true);
            }

            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("id"),
                    getRatePolicyForBookResponse,
                    (long)i);
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("discountRate"),
                    getRatePolicyForBookResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("minimumOrderAmount"),
                    getRatePolicyForBookResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("maximumDiscountPrice"),
                    getRatePolicyForBookResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("expirationPeriodStart"),
                    getRatePolicyForBookResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("expirationPeriodEnd"),
                    getRatePolicyForBookResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("name"),
                    getRatePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("description"),
                    getRatePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("bookName"),
                    getRatePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForBookResponse.getClass().getDeclaredField("policyStatusName"),
                    getRatePolicyForBookResponse,
                    Integer.toString(i));

            list.add(getRatePolicyForBookResponse);
        }

        Mockito.when(policyService.getRatePoliciesForBook(1)).thenReturn(list);
        String url = "http://localhost:"+port +"/task/policies/rate/book";
        mockMvc.perform(get(url).param("page","1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[5].id").value(6))
                .andExpect(jsonPath("$[6].id").value(7))
                .andExpect(jsonPath("$[7].id").value(8))
                .andExpect(jsonPath("$[8].id").value(9))
                .andExpect(jsonPath("$[9].id").value(10));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정률정책 for Category들을 - Pagenation 해서 받아오기")
    void getRatePoliciesForCategoryTest() throws Exception {

        List<GetRatePolicyForCategoryResponse> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            GetRatePolicyForCategoryResponse getRatePolicyForCategoryResponse = new GetRatePolicyForCategoryResponse();
            for(Field field : getRatePolicyForCategoryResponse.getClass().getDeclaredFields()){
                field.setAccessible(true);
            }

            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("id"),
                    getRatePolicyForCategoryResponse,
                    (long)i);
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("discountRate"),
                    getRatePolicyForCategoryResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("minimumOrderAmount"),
                    getRatePolicyForCategoryResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("maximumDiscountPrice"),
                    getRatePolicyForCategoryResponse,
                    i);
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("expirationPeriodStart"),
                    getRatePolicyForCategoryResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("expirationPeriodEnd"),
                    getRatePolicyForCategoryResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("name"),
                    getRatePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("description"),
                    getRatePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("categoryName"),
                    getRatePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getRatePolicyForCategoryResponse.getClass().getDeclaredField("policyStatusName"),
                    getRatePolicyForCategoryResponse,
                    Integer.toString(i));

            list.add(getRatePolicyForCategoryResponse);
        }

        Mockito.when(policyService.getRatePoliciesForCategory(1)).thenReturn(list);
        String url = "http://localhost:"+port +"/task/policies/rate/category";
        mockMvc.perform(get(url).param("page","1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[5].id").value(6))
                .andExpect(jsonPath("$[6].id").value(7))
                .andExpect(jsonPath("$[7].id").value(8))
                .andExpect(jsonPath("$[8].id").value(9))
                .andExpect(jsonPath("$[9].id").value(10));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정액정책 for Book들을 - Pagenation 해서 받아오기")
    void getPricePoliciesForBookTest() throws Exception {

        List<GetPricePolicyForBookResponse> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            GetPricePolicyForBookResponse getPricePolicyForBookResponse = new GetPricePolicyForBookResponse();
            for(Field field : getPricePolicyForBookResponse.getClass().getDeclaredFields()){
                field.setAccessible(true);
            }

            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("id"),
                    getPricePolicyForBookResponse,
                    (long)i);
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("minimumOrderAmount"),
                    getPricePolicyForBookResponse,
                    i);
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("discountPrice"),
                    getPricePolicyForBookResponse,
                    i);
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("expirationPeriodStart"),
                    getPricePolicyForBookResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("expirationPeriodEnd"),
                    getPricePolicyForBookResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("name"),
                    getPricePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("description"),
                    getPricePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("bookName"),
                    getPricePolicyForBookResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForBookResponse.getClass().getDeclaredField("policyStatusName"),
                    getPricePolicyForBookResponse,
                    Integer.toString(i));

            list.add(getPricePolicyForBookResponse);
        }

        Mockito.when(policyService.getPricePoliciesForBook(1)).thenReturn(list);
        String url = "http://localhost:"+port +"/task/policies/price/book";
        mockMvc.perform(get(url).param("page","1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[5].id").value(6))
                .andExpect(jsonPath("$[6].id").value(7))
                .andExpect(jsonPath("$[7].id").value(8))
                .andExpect(jsonPath("$[8].id").value(9))
                .andExpect(jsonPath("$[9].id").value(10));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정액정책 for Category들을 - Pagenation 해서 받아오기")
    void getPricePoliciesForCategoryTest() throws Exception {

        List<GetPricePolicyForCategoryResponse> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            GetPricePolicyForCategoryResponse getPricePolicyForCategoryResponse = new GetPricePolicyForCategoryResponse();
            for(Field field : getPricePolicyForCategoryResponse.getClass().getDeclaredFields()){
                field.setAccessible(true);
            }

            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("id"),
                    getPricePolicyForCategoryResponse,
                    (long)i);
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("minimumOrderAmount"),
                    getPricePolicyForCategoryResponse,
                    i);
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("discountPrice"),
                    getPricePolicyForCategoryResponse,
                    i);
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("expirationPeriodStart"),
                    getPricePolicyForCategoryResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("expirationPeriodEnd"),
                    getPricePolicyForCategoryResponse,
                    LocalDateTime.of(2024,1,i,12,0));
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("name"),
                    getPricePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("description"),
                    getPricePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("categoryName"),
                    getPricePolicyForCategoryResponse,
                    Integer.toString(i));
            ReflectionUtils.setField(
                    getPricePolicyForCategoryResponse.getClass().getDeclaredField("policyStatusName"),
                    getPricePolicyForCategoryResponse,
                    Integer.toString(i));

            list.add(getPricePolicyForCategoryResponse);
        }

        Mockito.when(policyService.getPricePoliciesForCategory(1)).thenReturn(list);
        String url = "http://localhost:"+port +"/task/policies/price/category";
        mockMvc.perform(get(url).param("page","1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[5].id").value(6))
                .andExpect(jsonPath("$[6].id").value(7))
                .andExpect(jsonPath("$[7].id").value(8))
                .andExpect(jsonPath("$[8].id").value(9))
                .andExpect(jsonPath("$[9].id").value(10));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정률정책 for Book 하나를 - ID로 받아오기")
    void getRatePolicyForBookTest() throws Exception {

        GetRatePolicyForBookResponse getRatePolicyForBookResponse =
                new GetRatePolicyForBookResponse(
                        1L,
                        10,
                        10000,
                        2000,
                        LocalDateTime.of(2024,1,1,1,1),
                        LocalDateTime.of(2024,1,10,1,1),
                        "테스트용 정률정책 for Book",
                        "테스트용 정률정책 for Book",
                        "테스트용 도서",
                        "테스트용 정책상태"
                );
        Mockito.when(policyService.getRatePolicyForBook(1L)).thenReturn(getRatePolicyForBookResponse);

        mockMvc.perform(get("/task/policies/rate/book/{id}", 1))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.discountRate").value(10))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.maximumDiscountPrice").value(2000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T01:01:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T01:01:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정률정책 for Book"))
                .andExpect(jsonPath("$.description").value("테스트용 정률정책 for Book"))
                .andExpect(jsonPath("$.bookName").value("테스트용 도서"))
                .andExpect(jsonPath("$.policyStatusName").value("테스트용 정책상태"));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정률정책 for Category 하나를 - ID로 받아오기")
    void getRatePolicyForCategoryTest() throws Exception {

        GetRatePolicyForCategoryResponse getRatePolicyForCategoryResponse =
                new GetRatePolicyForCategoryResponse(
                        1L,
                        10,
                        10000,
                        2000,
                        LocalDateTime.of(2024,1,1,1,1),
                        LocalDateTime.of(2024,1,10,1,1),
                        "테스트용 정률정책 for Category",
                        "테스트용 정률정책 for Category",
                        "테스트용 카테고리",
                        "테스트용 정책상태"
                );
        Mockito.when(policyService.getRatePolicyForCategory(1L)).thenReturn(getRatePolicyForCategoryResponse);

        mockMvc.perform(get("/task/policies/rate/category/{id}", 1))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.discountRate").value(10))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.maximumDiscountPrice").value(2000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T01:01:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T01:01:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정률정책 for Category"))
                .andExpect(jsonPath("$.description").value("테스트용 정률정책 for Category"))
                .andExpect(jsonPath("$.categoryName").value("테스트용 카테고리"))
                .andExpect(jsonPath("$.policyStatusName").value("테스트용 정책상태"));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정액정책 for Book 하나를 - ID로 받아오기")
    void getPricePolicyForBookTest() throws Exception {

        GetPricePolicyForBookResponse getPricePolicyForBookResponse =
                new GetPricePolicyForBookResponse(
                        1L,
                        10000,
                        1000,
                        LocalDateTime.of(2024,1,1,1,1),
                        LocalDateTime.of(2024,1,10,1,1),
                        "테스트용 정액정책 for Book",
                        "테스트용 정액정책 for Book",
                        "테스트용 도서",
                        "테스트용 정책상태"
                );
        Mockito.when(policyService.getPricePolicyForBook(1L)).thenReturn(getPricePolicyForBookResponse);

        mockMvc.perform(get("/task/policies/price/book/{id}", 1))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.discountPrice").value(1000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T01:01:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T01:01:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정액정책 for Book"))
                .andExpect(jsonPath("$.description").value("테스트용 정액정책 for Book"))
                .andExpect(jsonPath("$.bookName").value("테스트용 도서"))
                .andExpect(jsonPath("$.policyStatusName").value("테스트용 정책상태"));
    }

    @Test
    @DisplayName("관리자가 - 등록되어있는 - 정액정책 for Category 하나를 - ID로 받아오기")
    void getPricePolicyForCategoryTest() throws Exception {

        GetPricePolicyForCategoryResponse getPricePolicyForCategoryResponse =
                new GetPricePolicyForCategoryResponse(
                        1L,
                        10000,
                        1000,
                        LocalDateTime.of(2024,1,1,1,1),
                        LocalDateTime.of(2024,1,10,1,1),
                        "테스트용 정액정책 for Category",
                        "테스트용 정액정책 for Category",
                        "테스트용 도서",
                        "테스트용 정책상태"
                );
        Mockito.when(policyService.getPricePolicyForCategory(1L)).thenReturn(getPricePolicyForCategoryResponse);

        mockMvc.perform(get("/task/policies/price/category/{id}", 1))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.minimumOrderAmount").value(10000))
                .andExpect(jsonPath("$.discountPrice").value(1000))
                .andExpect(jsonPath("$.expirationPeriodStart")
                        .value("2024-01-01T01:01:00"))
                .andExpect(jsonPath("$.expirationPeriodEnd")
                        .value("2024-01-10T01:01:00"))
                .andExpect(jsonPath("$.name").value("테스트용 정액정책 for Category"))
                .andExpect(jsonPath("$.description").value("테스트용 정액정책 for Category"))
                .andExpect(jsonPath("$.categoryName").value("테스트용 도서"))
                .andExpect(jsonPath("$.policyStatusName").value("테스트용 정책상태"));
    }
}