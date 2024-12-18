package com.nhnacademy.taskapi.author.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorUpdateDTO {
    private int authorId;
    private String authorName;
}
