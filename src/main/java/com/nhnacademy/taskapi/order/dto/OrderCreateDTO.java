package com.nhnacademy.taskapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
    String orderer;
    String phoneNumber;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
}
