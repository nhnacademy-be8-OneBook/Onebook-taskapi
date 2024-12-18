package com.nhnacademy.taskapi.stock.service;

import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.dto.StockCreateUpdateDTO;

public interface StockService {
    Stock addStock(StockCreateUpdateDTO dto);
    Stock updateStock(StockCreateUpdateDTO dto);
}
