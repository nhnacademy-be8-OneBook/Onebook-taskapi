package com.nhnacademy.taskapi.Tag.domain;

import com.nhnacademy.taskapi.book.domain.Book;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tags")
@EntityListeners(AuditingEntityListener.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name")
    private String name;


    public Tag() {}

    @Builder
    public Tag(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }
}