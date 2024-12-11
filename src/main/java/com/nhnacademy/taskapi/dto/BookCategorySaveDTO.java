package com.nhnacademy.taskapi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCategorySaveDTO {
    private long bookId;
    private int categoryId;
}
