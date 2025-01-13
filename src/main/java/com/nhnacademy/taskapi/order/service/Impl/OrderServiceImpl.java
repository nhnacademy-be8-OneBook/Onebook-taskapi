package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.delivery.service.DeliveryService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.*;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderDetail;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import com.nhnacademy.taskapi.order.exception.OrderNotFoundException;
import com.nhnacademy.taskapi.order.exception.OrderStatusNotFoundException;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.order.repository.OrderStatusRepository;
import com.nhnacademy.taskapi.order.service.OrderDetailService;
import com.nhnacademy.taskapi.order.service.OrderService;
import com.nhnacademy.taskapi.order.service.PricingService;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import com.nhnacademy.taskapi.packaging.exception.PackagingNotAvailableException;
import com.nhnacademy.taskapi.packaging.exception.PackagingNotFoundException;
import com.nhnacademy.taskapi.packaging.repository.PackagingRepository;
import com.nhnacademy.taskapi.packaging.service.PackagingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
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
                waitingStatus
        );
        Order saveOrder = orderRepository.save(order);

        // 2. 주문 상세 저장
        orderDetailService.saveOrderDetail(saveOrder, orderFormRequest.getItems());

        // 3. 배송 저장
        deliveryService.createDelivery(saveOrder, orderFormRequest.getDelivery());

        return saveOrder.getOrderId();
    }

    @Override
    public long saveOrder(Long memberId, OrderFormRequest orderFormRequest) {
        // 맴버
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("Member id " + memberId + " does not exist"));

        // 주문 상태 default 값 대기
        OrderStatus waitingStatus = orderStatusRepository.findByStatusName("결제대기").orElseThrow(() -> new OrderStatusNotFoundException("OrderStatus is not found; error!!"));

        // 포장지 가능 여부
        packagingValidator.validatePackaging(orderFormRequest.getPackagingId(),
                orderFormRequest.getItems().size(),
                packagingRepository);

        // 책 불러오기
        // 총 가격 결정
        HashMap<Book, Integer> map = new HashMap<>();
        int totalPrice = 0;
        String bookTitle = null;
        for (BookOrderRequest item : orderFormRequest.getItems()) {
            Book book = bookRepository.findById(item.getBookId()).orElseThrow(() -> new BookNotFoundException("Book id " + item.getBookId() + " does not exist"));
            bookTitle = book.getTitle();
            totalPrice += book.getPrice();
            // 전부 가져와서 조회.

            map.put(book, item.getQuantity());
        }

        // 주문 만들기
        Order order = new Order(
                findMember,
                orderFormRequest.getDelivery().getOrdererName(),
                orderFormRequest.getDelivery().getOrdererPhoneNumber(),
                LocalDateTime.now(),
                totalPrice > 30000 ? 5000 : 0,
                totalPrice,
                bookTitle + "외 " + (map.size() - 1),
                packagingRepository.findById(orderFormRequest.getPackagingId()).orElseThrow(() -> new PackagingNotFoundException(orderFormRequest.getPackagingId() + "is not found.")),
                waitingStatus
        );

        orderRepository.save(order);

        return order.getOrderId();
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