package com.nhnacademy.taskapi.stock.controller;


import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.dto.StockCreateUpdateDTO;
import com.nhnacademy.taskapi.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;


    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody StockCreateUpdateDTO dto){
        Stock stock = stockService.addStock(dto);
        return ResponseEntity.ok().body(stock);
    }


    @PutMapping
    public ResponseEntity<Stock> modifyStock(@RequestBody StockCreateUpdateDTO dto){
        Stock stock = stockService.updateStock(dto);
        return ResponseEntity.ok().body(stock);
    }

}
