package com.nhnacademy.taskapi.review.repository;

import com.nhnacademy.taskapi.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
