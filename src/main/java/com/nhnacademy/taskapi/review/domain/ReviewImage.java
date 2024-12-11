package com.nhnacademy.taskapi.review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewImage {

    @Id
    private long imageId;

    private String imageUrl;

    @ManyToOne
    private Review review;
}
