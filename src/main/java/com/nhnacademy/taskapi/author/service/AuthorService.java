package com.nhnacademy.taskapi.author.service;

import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.dto.AuthorUpdateDTO;

public interface AuthorService {
    Author addAuthor(String name);
    Author updateAuthor(AuthorUpdateDTO dto);
    void deleteAuthor(int authorId);
    Author getAuthor(int authorId);
}
