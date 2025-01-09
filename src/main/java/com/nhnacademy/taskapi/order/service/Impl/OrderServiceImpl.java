package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderResponseDto;
import com.nhnacademy.taskapi.order.dto.OrderStatusResponseDto;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import com.nhnacademy.taskapi.order.exception.OrderNotFoundException;
import com.nhnacademy.taskapi.order.exception.OrderStatusNotFoundException;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.order.repository.OrderStatusRepository;
import com.nhnacademy.taskapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderStatusRepository orderStatusRepository;

    // create
    @Override
    public void saveOrder(Long memberId, OrderCreateDTO orderCreateDTO) {
//        if (!memberRepository.existsById(memberId)) {
//            throw new MemberIllegalArgumentException("Member id " + memberId + " does not exist");
//        }

        // 주문 상태 default 값 대기
        OrderStatus waitingStatus = orderStatusRepository.findByStatusName("결제대기").orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member id " + memberId + " does not exist"));

        Order order = new Order(
            findMember,
            orderCreateDTO.getOrderer(),
            orderCreateDTO.getPhoneNumber(),
            orderCreateDTO.getDateTime(),
            orderCreateDTO.getDeliveryPrice(),
            orderCreateDTO.getTotalPrice(),
            waitingStatus
        );
        orderRepository.save(order);
    }

    // read
    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDto> getOrderList(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id" + memberId + " dose not exist"));

        List<OrderResponseDto> dtoList = orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponseDto::fromOrder).toList();

        return dtoList;
    }

    @Override
    public OrdererResponseDto getOrderer(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id" + memberId + " dose not exist"));

        String ordererName = memberRepository.findById(memberId).get().getName();
        String ordererPhoneNumber = memberRepository.findById(memberId).get().getPhoneNumber();
        // 배송지 별명
        String recipientAlias;
        String recipientName;
        String recipientPhoneNumber;
        String recipientAddress;
        // 요청사항
        String recipientRequestedTerm;

        return null;
    }

    @Override
    public List<OrderStatusResponseDto> getOrdersByStatusName(String statusName) {
        orderStatusRepository.findByStatusName(statusName).orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        List<OrderStatusResponseDto> byStatusName = orderRepository.findByStatusName(statusName).stream().map(
                order -> OrderStatusResponseDto.fromOrderStatus(order)
        ).toList();
        return byStatusName;
    }

    @Override
    public void updateOrderStatus(List<Long> orderIds, String status) {
        OrderStatus newOrderStatus = orderStatusRepository.findByStatusName(status).orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        for (Long orderId : orderIds) {
            Order newOrder = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order id " + orderId + " does not exist"));
            newOrder.setOrderStatus(newOrderStatus);
        }
    }

    //    public List<OrderDetail> getOrderDetailList(Long orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order id " + orderId + " does not exist"));
//        return order.getOrderDetailList();
//    }
}