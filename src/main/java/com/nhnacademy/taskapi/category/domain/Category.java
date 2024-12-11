package com.nhnacademy.taskapi.category.domain;

import com.nhnacademy.taskapi.book.domain.BookCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @ManyToOne(optional = false)
    private BookCategory bookCategory;


    @NotBlank
    @Length(max = 20)
    private String name;
}
