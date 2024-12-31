package com.nhnacademy.taskapi.book.controller;


import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/book")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestPart(value = "dto") BookSaveDTO dto,
                                        @RequestPart(value = "image") MultipartFile image){
        Book book = bookService.saveBook(dto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
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
    public ResponseEntity<Page<Book>> bestsellersBook(Pageable pageable){
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
