package com.nhnacademy.taskapi.book.domain;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  bookId;


    // 출판사 - 책 단방향 다대일
    @ManyToOne(optional = false)
    private Publisher publisher;

    @NotBlank
    @Length(max = 100)
    private String title;

//    @NotBlank
//    @Length(max = 100)
//    private String content;

    @NotBlank
    private String description;

    @NotBlank
    @Length(max = 20)
    private String isbn13;

    @NotNull
    private int price;

    @NotNull
    private int salePrice;


    private long amount;


    private long views;



    private LocalDate pubdate;






}
