package com.nhnacademy.taskapi.author.controller;


import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.dto.AuthorUpdateDTO;
import com.nhnacademy.taskapi.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/{authorName}")
    public ResponseEntity<Author> createAuthor(@PathVariable String authorName){
        Author author = authorService.addAuthor(authorName);
        return ResponseEntity.ok().body(author);
    }

    @PutMapping
    public ResponseEntity<Author> modifyAuthor(@RequestBody AuthorUpdateDTO dto){
        Author author = authorService.updateAuthor(dto);
        return ResponseEntity.ok().body(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable int authorId){
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }

}
