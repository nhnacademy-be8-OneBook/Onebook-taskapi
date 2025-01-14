package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.delivery.service.DeliveryService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.*;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import com.nhnacademy.taskapi.order.exception.OrderNotFoundException;
import com.nhnacademy.taskapi.order.exception.OrderStatusNotFoundException;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.order.repository.OrderStatusRepository;
import com.nhnacademy.taskapi.order.service.OrderDetailService;
import com.nhnacademy.taskapi.order.service.OrderService;
import com.nhnacademy.taskapi.order.service.PricingService;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import com.nhnacademy.taskapi.packaging.repository.PackagingRepository;
import com.nhnacademy.taskapi.packaging.service.PackagingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final BookRepository bookRepository;
    private final PackagingRepository packagingRepository;
    private final DeliveryService deliveryService;

    private final PackagingValidator packagingValidator;
    private final PricingService pricingService;
    private final OrderDetailService orderDetailService;

    // create
    @Transactional
    @Override
    public long processOrder(Long memberId, OrderFormRequest orderFormRequest) {
        // 1. 주문 상태 확인
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id " + memberId + " does not exist"));
        OrderStatus waitingStatus = orderStatusRepository.findByStatusName("결제대기").orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        // 2. 포장지 가능 여부 확인
        Packaging packaging = packagingValidator.validatePackaging(orderFormRequest.getPackagingId(),
                orderFormRequest.getItems().size(),
                packagingRepository);

        // 3. 가격 계산
        int totalBookSalePrice = pricingService.calculatorToTalPriceByOrderRequest(orderFormRequest.getItems(), bookRepository);
        int DeliveryFee = pricingService.calculatorDeliveryFee(totalBookSalePrice);

        // 1. 주문 저장
        Order order = new Order(
                findMember,
                orderFormRequest.getDelivery().getOrdererName(),
                orderFormRequest.getDelivery().getOrdererPhoneNumber(),
                LocalDateTime.now(),
                DeliveryFee,
                totalBookSalePrice,
                "bookTitle",
                packaging,
                packaging.getPrice(),
                waitingStatus
        );
        Order saveOrder = orderRepository.save(order);

        // 2. 주문 상세 저장
        orderDetailService.saveOrderDetail(saveOrder, orderFormRequest.getItems());

        // 3. 배송 저장
        deliveryService.createDelivery(saveOrder, orderFormRequest.getDelivery());

        return saveOrder.getOrderId();
    }

    // read
    @Transactional(readOnly = true)
    @Override
    public List<OrderResponse> getOrderList(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id" + memberId + " dose not exist"));

        List<OrderResponse> dtoList = orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponse::fromOrder).toList();

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
    public List<OrderStatusResponse> getOrdersByStatusName(String statusName) {
        OrderStatus orderStatus = orderStatusRepository.findByStatusName(statusName).orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        List<OrderStatusResponse> byStatusName = orderRepository.findByOrderStatus(orderStatus).stream().map(
                order -> OrderStatusResponse.fromOrderStatus(order)
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