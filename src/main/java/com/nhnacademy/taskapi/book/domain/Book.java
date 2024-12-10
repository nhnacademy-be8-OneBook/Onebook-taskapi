package com.nhnacademy.taskapi.book.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long  bookId;

    @ManyToOne(optional = false)
    private Publisher publisher;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String description;

    @NotBlank
    private String isbn13;

    @NotNull
    private int price;

    @NotNull
    private BigDecimal salePrice;


    private long amount;


    private long views;



    private LocalDate pubdate;






}
