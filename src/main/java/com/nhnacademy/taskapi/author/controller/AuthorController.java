package com.nhnacademy.taskapi.author.controller;


import com.nhnacademy.taskapi.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;


}
