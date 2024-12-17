package com.nhnacademy.taskapi.book.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDTO {
    private String title;

    private String content;

    private String description;

    private int price;

    private int salePrice;

    private int stock;

}
