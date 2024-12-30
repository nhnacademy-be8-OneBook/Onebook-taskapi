package com.nhnacademy.taskapi.book.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookAladinDTO {

    private String title;
    private String authorName;
    private LocalDate pubdate;
    private String description;
    private String isbn13;
    private int priceSales;
    private int priceStandard;
    private String categoryNames;
    private String publisherName;
    private long salesPoint;
}
