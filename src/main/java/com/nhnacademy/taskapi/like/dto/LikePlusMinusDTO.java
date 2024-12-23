package com.nhnacademy.taskapi.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikePlusMinusDTO {
    private long bookId;
    private long memberId;
}
