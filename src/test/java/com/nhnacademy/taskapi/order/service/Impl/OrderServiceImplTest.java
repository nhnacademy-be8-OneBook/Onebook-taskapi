package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.entity.OrderStatus;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
import com.nhnacademy.taskapi.order.repository.OrderStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStatusRepository orderStatusRepository;

//    private OrderCreateDTO orderCreateDTO;

    @BeforeEach
    void setUp() {
//        orderCreateDTO = new OrderCreateDTO(
//                "김선준",
//                "010-9999-9999",
//                LocalDateTime.now(),
//                3000,
//                25000);
        MockitoAnnotations.openMocks(this);
    }



    @Test
    @DisplayName("Member 존재하지 않는 예외 발생")
    void saveMember() {
        // given
        Long memberId = 1L;
        when(orderStatusRepository.findByStatusName("결제대기")).thenReturn(Optional.of(new OrderStatus("결제대기")));
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO("김선준", "010-9999-9999", LocalDateTime.now(), 3000, 25000);
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // when
        assertThrows(MemberNotFoundException.class,
                () -> orderService.saveOrder(memberId, orderCreateDTO));

        // then
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("Order 저장 성공")
    void saveOrder() {
        // given
        Long memberId = 1L;
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO("김선준", "010-9999-9999", LocalDateTime.now(), 3000, 25000);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember()));
        when(orderStatusRepository.findByStatusName("결제대기")).thenReturn(Optional.of(new OrderStatus("결제대기")));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocatoin -> {
            Order order = invocatoin.getArgument(0);
            order.setOrderId(1L);
            return order;
        });

        // when
        Long orderId = orderService.saveOrder(1L, orderCreateDTO);

        // then
        assertNotNull(orderId);
        assertEquals(1L, orderId);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Member Id로 주문 조회")
    void getOrderList() {
        // given
        Long memberId = 1L;
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO("김선준", "010-9999-9999", LocalDateTime.now(), 3000, 25000);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember()));
        when(orderRepository.findAllByMemberId(memberId)).thenReturn(mockOrderList());

        // when
        orderService.getOrderList(memberId);

        // then
        verify(orderRepository, times(1)).findAllByMemberId(memberId);

    }

    private Member mockMember() {
        return mock(Member.class);
    }

    private List<Order> mockOrderList() {
        return List.of(
                new Order(null, "홍길동", "010-1111-2222", LocalDateTime.now(), 3000, 25000),
                new Order(null, "김철수", "010-2222-3333", LocalDateTime.now().plusDays(1), 3500, 40000)
        );
    }
}