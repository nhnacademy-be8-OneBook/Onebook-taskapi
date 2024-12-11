package com.nhnacademy.taskapi.stock.domain;


import com.nhnacademy.taskapi.book.domain.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stockId;


    @ManyToOne(optional = false)
    private Book book;


    @NotNull
    private int stock;
}
