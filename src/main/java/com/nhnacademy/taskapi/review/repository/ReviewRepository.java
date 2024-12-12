package com.nhnacademy.taskapi.review.repository;

import com.nhnacademy.taskapi.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.grade) FROM Review r WHERE r.book.bookId = :bookId")
    Optional<Double> findAverageGradeByBookId(long bookId);
}
