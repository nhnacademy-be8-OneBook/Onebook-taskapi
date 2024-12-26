package com.nhnacademy.taskapi.packaging.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Packaging {
    @Id
    @Column(name = "packagings")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;
    int price;

    public Packaging(String name, int price) {
        this.name = name;
        this.price = price;
    }
}