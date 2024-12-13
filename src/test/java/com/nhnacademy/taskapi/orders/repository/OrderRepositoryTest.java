package com.nhnacademy.taskapi.orders.repository;

import com.nhnacademy.taskapi.orders.dto.SaveOrders;
import com.nhnacademy.taskapi.orders.entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;
//
//    @Sql("/repository/order-test.sql")
//    @BeforeAll
//    static void setup() {
//        log.info("setup");
//    }

    // create
    @Test
    void save() {
        log.info("order DB 넣기 전 count !!!!! : {}", orderRepository.findAll().isEmpty());

        Orders orders = orderRepository.save(new Orders(
                "010-9999-9999", LocalDateTime.now(), 3000, 25000)
        );

        log.info("order DB 넣은 후 count !!!!! : {}", orderRepository.findAll().isEmpty());
        entityManager.flush();

        List<Orders> findOrdersList = orderRepository.findByPhoneNumber("010-9999-9999");
        System.out.println("findOrderList !!!!!!");
        findOrdersList.forEach(x -> System.out.println(x.toString()));
    }

    // read
    @Test
    void findOrder() {
        Long id = 1L;
        Orders orders = orderRepository.findById(id).orElse(null);

        Assertions.assertThat(orders).isNotNull();
        Assertions.assertThat(orders.getPhoneNumber()).isEqualTo("010-1111-1111");
        Assertions.assertThat(orders.getDeliveryPrice()).isEqualTo(3000);
        Assertions.assertThat(orders.getTotalPrice()).isEqualTo(25000);
    }

    // update
    @Sql("/repository/order-test.sql")
    @Test
    void updateOrders() {
        Long id = 1L;
        Orders orders = orderRepository.findById(id).orElse(null);
        orders.setPhoneNumber("010-1234-1234");
        orders.setTotalPrice(5000);
        orderRepository.saveAndFlush(orders);

        Orders orders1 = orderRepository.findById(id).orElse(null);
        Assertions.assertThat(orders).isNotNull();
        Assertions.assertThat(orders1.getPhoneNumber()).isEqualTo("010-1234-1234");
        Assertions.assertThat(orders1.getTotalPrice()).isEqualTo(5000);
    }
}