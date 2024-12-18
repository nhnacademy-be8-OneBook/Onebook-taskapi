package com.nhnacademy.taskapi.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockCreateUpdateDTO {
    private long bookId;
    private int amount;
}
