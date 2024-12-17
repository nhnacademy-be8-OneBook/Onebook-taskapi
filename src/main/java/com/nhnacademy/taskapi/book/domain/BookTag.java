package com.nhnacademy.taskapi.book.domain;


import com.nhnacademy.taskapi.Tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "books_tags")
public class BookTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookTagId;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Tag tag;
}
