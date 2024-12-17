package com.nhnacademy.taskapi.image.repository;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByImageUrl(String imageUrl);
    Optional<Image> findByBook(Book book);
}
