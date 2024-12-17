package com.nhnacademy.taskapi.packaging.entity;

import jakarta.persistence.*;

@Entity
public class Packaging {
    @Id
    @Column(name = "packagings")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;
    int price;
}