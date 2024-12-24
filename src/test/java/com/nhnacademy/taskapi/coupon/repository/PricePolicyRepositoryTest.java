package com.nhnacademy.taskapi.coupon.repository;

import com.nhnacademy.taskapi.coupon.domain.entity.PricePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;


@DataJpaTest
class PricePolicyRepositoryTest {

    @Autowired
    private PricePolicyRepository pricePolicyRepository;

    @Test
    @DisplayName("PricePolicy Repository save 테스트")
    void savePricePolicyTest() throws NoSuchFieldException {

        PricePolicy pricePolicy = new PricePolicy();

        for(Field filed :pricePolicy.getClass().getDeclaredFields()){
            filed.setAccessible(true);
        }

        ReflectionUtils.setField(pricePolicy.getClass().
                getDeclaredField("pricePolicyId"), pricePolicy, 1L);
        ReflectionUtils.setField(pricePolicy.getClass().
                getDeclaredField("name"), pricePolicy, "테스트정액정책1");
        ReflectionUtils.setField(pricePolicy.getClass()
                .getDeclaredField("minimumOrderAmount"),pricePolicy,10000 );
        ReflectionUtils.setField(pricePolicy.getClass()
                .getDeclaredField("discountAmount"),pricePolicy ,10);

        pricePolicyRepository.save(pricePolicy);

    }



}