package com.nhnacademy.taskapi.like.domain;


import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.member.domain.Member;
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
@Table(name = "Likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;


    @ManyToOne(optional = false)
    private Member member;


    @ManyToOne(optional = false)
    private Book book;
}
