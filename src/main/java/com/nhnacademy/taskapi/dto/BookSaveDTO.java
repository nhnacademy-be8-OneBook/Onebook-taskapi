package com.nhnacademy.taskapi.dto;


import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSaveDTO {
    private String title;
    private String content;
    private String description;
    private String isbn13;
    private int price;
    private int salePrice;
    private int amount;
    private LocalDate pubDate;

}
