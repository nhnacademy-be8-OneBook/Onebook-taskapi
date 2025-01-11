package com.nhnacademy.taskapi.stock.controller;


import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.dto.StockCreateUpdateDTO;
import com.nhnacademy.taskapi.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/stock")
public class StockController {
    private final StockService stockService;


    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody StockCreateUpdateDTO dto){
        Stock stock = stockService.addStock(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(stock);
    }


    @PutMapping
    public ResponseEntity<Stock> modifyStock(@RequestBody StockCreateUpdateDTO dto){
        Stock stock = stockService.updateStock(dto);
        return ResponseEntity.status(HttpStatus.OK).body(stock);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Stock> getStock(@PathVariable("bookId") long bookId){
        Stock stock = stockService.getStock(bookId);
        return ResponseEntity.ok().body(stock);
    }
}
