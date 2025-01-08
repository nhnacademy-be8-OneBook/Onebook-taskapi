package com.nhnacademy.taskapi.order.repository;

import com.nhnacademy.taskapi.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
