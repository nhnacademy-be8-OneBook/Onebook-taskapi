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
    private String authorName;
    private String pubdate;
    private String description;
    private String isbn13;
    private Integer priceSales;
    private Integer price;
    private String categoryNames;
    private String publisherName;
    private Long salesPoint;

    private String tagName;

    String imageUrl;
}
