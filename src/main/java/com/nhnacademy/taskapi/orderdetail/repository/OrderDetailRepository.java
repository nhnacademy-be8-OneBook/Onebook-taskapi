package com.nhnacademy.taskapi.orderdetail.repository;

import com.nhnacademy.taskapi.orderdetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}