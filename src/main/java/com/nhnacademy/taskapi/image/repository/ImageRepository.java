package com.nhnacademy.taskapi.image.repository;

import com.nhnacademy.taskapi.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
