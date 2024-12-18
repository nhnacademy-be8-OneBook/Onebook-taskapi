package com.nhnacademy.taskapi.Tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateDTO {
    private long tagId;
    private String tagName;
}
