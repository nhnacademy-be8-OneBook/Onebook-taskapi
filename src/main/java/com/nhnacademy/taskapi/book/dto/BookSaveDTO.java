package com.nhnacademy.taskapi.book.dto;


import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
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
    private Author author;
    private String content;
    private String pubdate;
    private String description;
    private String isbn13;
    private Integer priceSales;
    private Integer price;
    private int categoryId;
    private Publisher publisher;
    private Tag tag;
    private byte[] imageBytes;
    private String imageName;
    private Integer stock;
}
