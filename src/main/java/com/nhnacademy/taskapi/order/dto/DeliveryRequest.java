package com.nhnacademy.taskapi.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DeliveryRequest {
    String ordererName;
    String ordererPhoneNumber;
    String recipientAddress;
    String recipientRequestedTerm;
    String recipientName;
    String recipientPhone;
    LocalDate deliveryCompletedDate;
}
