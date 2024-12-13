package com.nhnacademy.taskapi.orderlist.repository;

import com.nhnacademy.taskapi.orderlist.entity.OrdersList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersListRepository extends JpaRepository<OrdersList, Long> {
}