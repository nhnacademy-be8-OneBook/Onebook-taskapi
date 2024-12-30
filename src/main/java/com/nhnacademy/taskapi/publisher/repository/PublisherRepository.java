package com.nhnacademy.taskapi.publisher.repository;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
    boolean existsByName(String name);
}
