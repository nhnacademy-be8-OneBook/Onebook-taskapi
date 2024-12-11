package com.nhnacademy.taskapi.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class PaymentMethod {

    @Id
    private String paymentKey;

    @NotNull
    private String type;

    @Column(nullable = true)
    private String method;
}
