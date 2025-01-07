package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.address.repository.AddressRepository;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.dto.OrderResponseDTO;
import com.nhnacademy.taskapi.order.dto.OrdererResponseDto;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
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
    private final AddressRepository addressRepository;

    // create
    @Override
    public void saveOrder(Long memberId, OrderCreateDTO orderCreateDTO) {
//        if (!memberRepository.existsById(memberId)) {
//            throw new MemberIllegalArgumentException("Member id " + memberId + " does not exist");
//        }

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member id " + memberId + " does not exist"));

        Order order = new Order(
            findMember,
            orderCreateDTO.getOrderer(),
            orderCreateDTO.getPhoneNumber(),
            orderCreateDTO.getDateTime(),
            orderCreateDTO.getDeliveryPrice(),
            orderCreateDTO.getTotalPrice()
        );
        orderRepository.save(order);
    }

    // read
    @Transactional(readOnly = true)
    @Override
    public List<OrderResponseDTO> getOrderList(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id" + memberId + " dose not exist"));

        List<OrderResponseDTO> dtoList = orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponseDTO::fromOrder).toList();

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

    //    public List<OrderDetail> getOrderDetailList(Long orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order id " + orderId + " does not exist"));
//        return order.getOrderDetailList();
//    }
}