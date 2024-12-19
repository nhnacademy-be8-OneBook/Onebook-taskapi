package com.nhnacademy.taskapi.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthorCreateDTO {
    private long bookId;
    private int authorId;
}
