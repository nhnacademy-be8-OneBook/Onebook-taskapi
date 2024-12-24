package com.nhnacademy.taskapi.coupon.repository;

import com.nhnacademy.taskapi.coupon.domain.entity.RatePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;

@DataJpaTest
class RatePolicyRepositoryTest {

    @Autowired
    private RatePolicyRepository ratePolicyRepository;

    @Test
    @DisplayName("RatePolicy Repository save 테스트")
    public void saveRatePolicyTest() throws NoSuchFieldException {

        RatePolicy ratePolicy = new RatePolicy();

        for(Field field : ratePolicy.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }

        ReflectionUtils.setField(ratePolicy.getClass()
                .getDeclaredField("ratePolicyId"), ratePolicy,1L);
        ReflectionUtils.setField(ratePolicy.getClass()
                .getDeclaredField("name"), ratePolicy,"테스트정률정책1");
        ReflectionUtils.setField(ratePolicy.getClass()
                .getDeclaredField("minimumOrderAmount"), ratePolicy,10000);
        ReflectionUtils.setField(ratePolicy.getClass().
                getDeclaredField("discountRate"), ratePolicy,10);
        ReflectionUtils.setField(ratePolicy.getClass()
                .getDeclaredField("maximumDiscountAmount"), ratePolicy,10000);

        ratePolicyRepository.save(ratePolicy);
    }

}