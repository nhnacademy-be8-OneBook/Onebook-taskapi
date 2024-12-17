package com.nhnacademy.taskapi.orders.repository;

import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
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

//    // create
//    @Test
//    void save() {
//        log.info("order DB 넣기 전 count !!!!! : {}", orderRepository.findAll().isEmpty());
//
//        Order order = orderRepository.save(new Order(
//                "010-9999-9999", LocalDateTime.now(), 3000, 25000)
//        );
//
//        log.info("order DB 넣은 후 count !!!!! : {}", orderRepository.findAll().isEmpty());
//        entityManager.flush();
//
//        List<Order> findOrderList = orderRepository.findByPhoneNumber("010-9999-9999");
//        System.out.println("findOrderList !!!!!!");
//        findOrderList.forEach(x -> System.out.println(x.toString()));
//    }
//
//    // read
//    @Test
//    void findOrder() {
//        Long id = 1L;
//        Order order = orderRepository.findById(id).orElse(null);
//
//        Assertions.assertThat(order).isNotNull();
//        Assertions.assertThat(order.getPhoneNumber()).isEqualTo("010-1111-1111");
//        Assertions.assertThat(order.getDeliveryPrice()).isEqualTo(3000);
//        Assertions.assertThat(order.getTotalPrice()).isEqualTo(25000);
//    }
//
//    // update
//    @Sql("/repository/order-test.sql")
//    @Test
//    void updateOrder() {
//        Long id = 1L;
//        Order order = orderRepository.findById(id).orElse(null);
//        order.setPhoneNumber("010-1234-1234");
//        order.setTotalPrice(5000);
//        orderRepository.saveAndFlush(order);
//
//        Order order1 = orderRepository.findById(id).orElse(null);
//        Assertions.assertThat(order).isNotNull();
//        Assertions.assertThat(order1.getPhoneNumber()).isEqualTo("010-1234-1234");
//        Assertions.assertThat(order1.getTotalPrice()).isEqualTo(5000);
//    }
//
//    // delete
//    @Sql("/repository/order-test.sql")
//    @Test
//    void deleteOrder() {
//        Long id = 1L;
//        orderRepository.deleteById(id);
//
//        Order order = orderRepository.findById(id).orElse(null);
//        Assertions.assertThat(order).isNull();
//    }
}