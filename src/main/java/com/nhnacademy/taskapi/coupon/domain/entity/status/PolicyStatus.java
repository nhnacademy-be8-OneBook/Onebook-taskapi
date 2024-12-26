package com.nhnacademy.taskapi.coupon.domain.entity.status;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PolicyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer policyStatusId;

    @Column(nullable = false, length = 30)
    private String name;
}
