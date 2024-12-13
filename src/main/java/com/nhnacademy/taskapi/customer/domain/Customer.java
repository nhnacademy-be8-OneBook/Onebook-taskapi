package com.nhnacademy.taskapi.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="customer", uniqueConstraints = {
        @UniqueConstraint(
                name="EMAIL_UNIQUE",
                columnNames = {"email"}
        )
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
