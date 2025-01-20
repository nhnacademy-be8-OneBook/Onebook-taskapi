//package com.nhnacademy.taskapi.config.batch;
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@RequiredArgsConstructor
//@Configuration
//public class CustomBatchConfig {
//
//    private final EntityManagerFactory entityManagerFactory;
//
//    // JpaTransactionManager 설정.
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//}
