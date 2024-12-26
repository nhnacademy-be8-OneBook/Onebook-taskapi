package com.nhnacademy.taskapi.cart.controller;

import com.nhnacademy.taskapi.cart.dto.CartRequestDto;
import com.nhnacademy.taskapi.cart.dto.CartResponseDto;
import com.nhnacademy.taskapi.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/carts")
public class CartController {

    private final CartService cartService;

    // 장바구니 단일 조회.
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable String id){

        CartResponseDto result = cartService.getCartById(id);
        return ResponseEntity.ok().body(result);

    }

    // 장바구니 저장.
    @PostMapping("/{id}")
    public ResponseEntity<CartResponseDto> registerCart(
            @PathVariable String id,
            @RequestHeader(value = "X-MEMBER-ID", required = false) Long memberId, // 없으면 null 로 받음.
            @RequestBody CartRequestDto cartRequestDto){

        CartResponseDto result = null;
        if(Objects.isNull(memberId)) {
            // 비회원
            result = cartService.registerCart(id, cartRequestDto);
        }else {
            // 회원
            result = cartService.registerMemberCart(id, memberId, cartRequestDto);
        }
        return ResponseEntity.ok().body(result);
    }

    // 장바구니 수정.
    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDto> modifyCart(
            @PathVariable String id,
            @RequestBody CartRequestDto cartRequestDto) {

        CartResponseDto cartResponseDto = cartService.modifyCart(id, cartRequestDto);
        return ResponseEntity.ok().body(cartResponseDto);
    }

    // 장바구니 삭제.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCart(@PathVariable String id) {
        cartService.removeCart(id);
        return ResponseEntity.noContent().build();
    }

}
