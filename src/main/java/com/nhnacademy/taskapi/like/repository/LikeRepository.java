package com.nhnacademy.taskapi.like.repository;

import com.nhnacademy.taskapi.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
