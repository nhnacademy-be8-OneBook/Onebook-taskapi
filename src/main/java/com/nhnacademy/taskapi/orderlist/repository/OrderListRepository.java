package com.nhnacademy.taskapi.orderlist.repository;

import com.nhnacademy.taskapi.orderlist.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
}