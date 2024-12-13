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
    String title;
    String authorName;
    String pubdate;
    String description;
    String isbn13;
    int priceSales;
    int price;
    String categoryNames;
    String publisherName;
    long salesPoint;

    String tagName;

    String imageUrl;
}
