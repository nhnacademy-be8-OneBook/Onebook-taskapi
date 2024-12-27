package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookSaveDTO dto){
        Book book = bookService.saveBook(dto);
        return ResponseEntity.ok().body(book);
    }

    @PutMapping("{bookId}")
    public ResponseEntity<Book> modifyBook(@PathVariable long bookId, @RequestBody BookUpdateDTO dto){
        Book book = bookService.updateBook(bookId, dto);
        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();    }


    @GetMapping("/bestsellers")
    public ResponseEntity<Page<Book>> bestsellersBook(@RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 50);
        Page<Book> books = bookService.bestSellerBooks(pageable);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/newbooks")
    public ResponseEntity<Page<Book>> newbooksBook(@RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 50);
        Page<Book> books = bookService.newBooks(pageable);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable long bookId){
        Book book = bookService.getBook(bookId);
        return ResponseEntity.ok().body(book);
    }


}
