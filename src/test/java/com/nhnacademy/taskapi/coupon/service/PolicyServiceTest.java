package com.nhnacademy.taskapi.coupon.service;

import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreatePricePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.CreateRatePolicyResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.PricePolicy;
import com.nhnacademy.taskapi.coupon.domain.entity.RatePolicy;
import com.nhnacademy.taskapi.coupon.repository.PricePolicyRepository;
import com.nhnacademy.taskapi.coupon.repository.RatePolicyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;


@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private PricePolicyRepository pricePolicyRepository;

    @Mock
    private RatePolicyRepository ratePolicyRepository;

    @InjectMocks
    private PolicyService policyService;

    @Test
    @DisplayName("정액 정책 생성시 - 정상적인 요청이면 - 정책이 저장되어야 한다.")
    void createPricePolicyTest() throws NoSuchFieldException {

        // given
        CreatePricePolicyRequest createPricePolicyRequest = new CreatePricePolicyRequest();

        for(Field field : createPricePolicyRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }

        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("name"),createPricePolicyRequest,"테스트정액정책1");
        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("minimumOrderAmount"),createPricePolicyRequest,10000);
        ReflectionUtils.setField(createPricePolicyRequest.getClass()
                .getDeclaredField("discountAmount"),createPricePolicyRequest,1000);


        PricePolicy pricePolicy = PricePolicy.createPricePolicy(createPricePolicyRequest);

        Mockito.when(pricePolicyRepository.save(Mockito.any(PricePolicy.class)))
                .thenReturn(pricePolicy);

        CreatePricePolicyResponse expected = CreatePricePolicyResponse.changeEntityToDto(pricePolicy);
        CreatePricePolicyResponse actual = policyService.createPricePolicy(createPricePolicyRequest);


        Mockito.verify(pricePolicyRepository,Mockito.times(1))
                .save(Mockito.any(PricePolicy.class));

        Assertions.assertAll(
                ()->{Assertions.assertEquals(expected.getName(), actual.getName());},
                ()->{Assertions.assertEquals(expected.getMinimumOrderAmount(), actual.getMinimumOrderAmount());},
                ()->{Assertions.assertEquals(expected.getDiscountAmount(), actual.getDiscountAmount());}
        );
    }

    @Test
    @DisplayName("정률 정책 생성시 - 정상적인 요청이면 - 정책이 저장되어야 한다.")
    void createRatePolicyTest() throws NoSuchFieldException {

        // given
        CreateRatePolicyRequest createRatePolicyRequest = new CreateRatePolicyRequest();

        for(Field field : createRatePolicyRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }

        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("name"),createRatePolicyRequest,"테스트정률정책1");
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("minimumOrderAmount"),createRatePolicyRequest,10000);
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("discountRate"),createRatePolicyRequest,10);
        ReflectionUtils.setField(createRatePolicyRequest.getClass()
                .getDeclaredField("maximumDiscountAmount"),createRatePolicyRequest,10000);

        RatePolicy ratePolicy = RatePolicy.createRatePolicy(createRatePolicyRequest);

        Mockito.when(ratePolicyRepository.save(Mockito.any(RatePolicy.class)))
                .thenReturn(ratePolicy);

        CreateRatePolicyResponse expected = CreateRatePolicyResponse.changeEntityToDto(ratePolicy);
        CreateRatePolicyResponse actual = policyService.createRatePolicy(createRatePolicyRequest);


        Mockito.verify(ratePolicyRepository,Mockito.times(1))
                .save(Mockito.any(RatePolicy.class));

        Assertions.assertAll(
                ()->{Assertions.assertEquals(expected.getName(), actual.getName());},
                ()->{Assertions.assertEquals(expected.getDiscountRate(), actual.getDiscountRate());},
                ()->{Assertions.assertEquals(expected.getMinimumOrderAmount(), actual.getMinimumOrderAmount());},
                ()->{Assertions.assertEquals(expected.getMaximumDiscountAmount(), actual.getMaximumDiscountAmount());}
        );
    }

}