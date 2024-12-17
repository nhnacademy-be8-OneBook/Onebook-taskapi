package com.nhnacademy.taskapi.stock.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.exception.InvalidStockAmountException;
import com.nhnacademy.taskapi.stock.exception.StockDuplicateException;
import com.nhnacademy.taskapi.stock.exception.StockNotFoundException;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final BookRepository bookRepository;


    @Override
    @Transactional
    public Stock addStock(long bookId, int amount) {
        if(amount < 0){
            throw new InvalidStockAmountException("stock must be greater than 0 ");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));

        Stock existStock = stockRepository.findByBook_bookId(bookId);

        if(Objects.nonNull(existStock)){
            throw new StockDuplicateException("The stock for the book already exists !");
        }

        Stock stock = new Stock();
        stock.setBook(book);
        stock.setStock(amount);
        return stockRepository.save(stock);
    }

    @Override
    @Transactional
    public Stock updateStock(long bookId, int newAmount) {
        Stock stock = stockRepository.findById(bookId).orElseThrow(() -> new StockNotFoundException("Stock not found"));

        stock.setStock(newAmount);
        return stockRepository.save(stock);
    }
}
