package com.nhnacademy.taskapi.author.service.Impl;

import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.dto.AuthorUpdateDTO;
import com.nhnacademy.taskapi.author.exception.AuthorNotFoundException;
import com.nhnacademy.taskapi.author.exception.InvalidAuthorNameException;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;


    // 작가등록
    @Override
    public Author addAuthor(String name) {
        if(Objects.isNull(name) || name.trim().isEmpty()){
            throw new InvalidAuthorNameException("this AuthorName is Null Or Empty !");
        }
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    //작가 수정
    @Override
    public Author updateAuthor(AuthorUpdateDTO dto){
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException("This Author Not Exist !"));

        if(Objects.isNull(dto.getAuthorName()) || dto.getAuthorName().trim().isEmpty()){
            throw new InvalidAuthorNameException("this AuthorName is Null Or Empty !");
        }

        author.setName(dto.getAuthorName());
        return authorRepository.save(author);
    }

    //작가 삭제
    @Override
    public void deleteAuthor(int authorId){
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException("This Author Not Exist !"));
        authorRepository.delete(author);
    }
}
