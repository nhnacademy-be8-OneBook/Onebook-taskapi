package com.nhnacademy.taskapi.cart.repository;

import com.nhnacademy.taskapi.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

}
//