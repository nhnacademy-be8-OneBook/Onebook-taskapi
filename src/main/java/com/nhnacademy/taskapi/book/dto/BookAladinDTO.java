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

    String title;
    String authorName;
    LocalDate pubdate;
    String description;
    String isbn13;
    int priceSales;
    int price;
    String categoryNames;
    String publisherName;
    long salesPoint;
}
