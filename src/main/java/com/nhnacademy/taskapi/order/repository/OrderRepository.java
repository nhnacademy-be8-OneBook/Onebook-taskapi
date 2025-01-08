package com.nhnacademy.taskapi.order.repository;

import com.nhnacademy.taskapi.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPhoneNumber(String s);
    List<Order> findAllByMemberId(Long memberId);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderStatus WHERE o.orderStatus.statusName = :statusName")
    List<Order> findByStatusName(@Param("statusName") String statusName);
}
