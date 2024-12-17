package com.nhnacademy.taskapi.book.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCategorySaveDTO {
    private int categoryId;
    private long bookId;
}
