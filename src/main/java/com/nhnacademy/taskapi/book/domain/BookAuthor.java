package com.nhnacademy.taskapi.book.domain;

import com.nhnacademy.taskapi.author.domain.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookAuthorId;


    @ManyToOne(optional = false)
    private Book book;


    @ManyToOne(optional = false)
    private Author author;
}
