package com.nhnacademy.taskapi.stock.service;

import com.nhnacademy.taskapi.stock.domain.Stock;

public interface StockService {
    Stock addStock(long bookId, int stock);
    Stock updateStock(long bookId, int newStock);
}
