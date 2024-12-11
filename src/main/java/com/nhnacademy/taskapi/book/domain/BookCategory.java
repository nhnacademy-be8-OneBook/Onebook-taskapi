package com.nhnacademy.taskapi.book.domain;


import com.nhnacademy.taskapi.category.domain.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCategoryId;


    @ManyToOne(optional = false)
    private Book book;


    @ManyToOne(optional = false)
    private Category category;


}