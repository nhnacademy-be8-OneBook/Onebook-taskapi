package com.nhnacademy.taskapi.cart.dto;

import com.nhnacademy.taskapi.cart.domain.Cart;
import com.nhnacademy.taskapi.cart.domain.CartItem;

import java.util.List;
import java.util.Objects;

public record CartRequestDto(
        List<CartItemRequestDto> cartItemRequestDtoList
) { }

