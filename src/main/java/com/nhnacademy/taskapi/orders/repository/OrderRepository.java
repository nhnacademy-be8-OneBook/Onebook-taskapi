package com.nhnacademy.taskapi.orders.repository;

import com.nhnacademy.taskapi.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByPhoneNumber(String phoneNumber);
}
