package com.nhnacademy.taskapi.stock.service.Impl;

import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final BookRepository bookRepository;


    @Override
    public Stock addStock(long bookId, int amount) {
        Stock stock = new Stock();
        stock.setBook(bookRepository.findById(bookId).get());
        stock.setStock(amount);
        return stockRepository.save(stock);
    }
}
