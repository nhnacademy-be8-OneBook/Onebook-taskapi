package com.nhnacademy.taskapi.order.repository;

import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrdererPhoneNumber(String s);
    // TODO Pageable을 적용하면서 생긴 에러?
    List<Order> findAllByMemberId(Long memberId);
    Page<Order> findAllByMemberId(Long memberId, Pageable pageable);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

//     TODO:QueryFactory 찾아보기
//    @Query("SELECT o FROM Order o JOIN FETCH o.orderStatus WHERE o.orderStatus.statusName = :statusName") // 캐시 사용불가
//    List<Order> findByStatusName(@Param("statusName") String statusName);
}
