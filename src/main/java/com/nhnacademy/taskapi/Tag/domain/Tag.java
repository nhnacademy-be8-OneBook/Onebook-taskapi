package com.nhnacademy.taskapi.Tag.domain;

import com.nhnacademy.taskapi.book.domain.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "Tags")
@EntityListeners(AuditingEntityListener.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long tagId;

    @Column(name = "tag_name")
    private String Name;

    @ManyToOne
    private Book book;

    public Tag() {}

    @Builder
    public Tag(Long tagId, Book book, String Name) {
        this.tagId = tagId;
        this.book = book;
        this.Name = Name;
    }
}