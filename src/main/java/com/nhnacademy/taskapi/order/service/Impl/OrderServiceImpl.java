package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.member.service.MemberService;
import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderDetailDTO;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;

    @Override
    public void saveOrder(Long member_id, OrderCreateDTO orderCreateDTO) {
        Order order = new Order(
            memberService.getMemberById(member_id),
            orderCreateDTO.getOrderer(),
            orderCreateDTO.getPhoneNumber(),
            orderCreateDTO.getDateTime(),
            orderCreateDTO.getDeliveryPrice(),
            orderCreateDTO.getTotalPrice()
        );
        orderRepository.save(order);
    }

    @Override
    public List<OrderDetailDTO> getOrders(Long memberId) {
        List<OrderDetailDTO> dtoList = orderRepository.findAllByMemberId(memberId).stream()
                .map(order -> new OrderDetailDTO(
                        order.getOrderer(),
                        order.getDateTime(),
                        order.getDeliveryPrice(),
                        order.getTotalPrice()
                )).toList();

        return dtoList;
    }
}