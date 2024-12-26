package com.nhnacademy.taskapi.order.service.Impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.order.dto.OrderCreateDTO;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.order.repository.OrderRepository;
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

    private Member mockMember() {
        return mock(Member.class);
    }

    @Test
    @DisplayName("Member 존재하지 않는 예외 발생")
    void saveMember() {
        // given
        Long memberId = 1L;
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

        // when
        orderService.saveOrder(1L, orderCreateDTO);

        // then
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Member Id로 주문 조회")
    void getOrderList() {
        // given
        Long memberId = 1L;
        when(orderRepository.findAllByMemberId(memberId)).then(mockOrderList());
    }

    private List<Order> mockOrderList() {
        return List.of(
                new Order(null, "홍길동", "010-1111-2222", LocalDateTime.now(), 3000, 25000),
                new Order(null, "김철수", "010-2222-3333", LocalDateTime.now().plusDays(1), 3500, 40000)
        );
    }
}