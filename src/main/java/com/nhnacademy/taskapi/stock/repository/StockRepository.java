package com.nhnacademy.taskapi.stock.repository;

import com.nhnacademy.taskapi.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByBook_bookId(Long bookId);
}
