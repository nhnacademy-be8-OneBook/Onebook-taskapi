package com.nhnacademy.taskapi.packaging.repository;

import com.nhnacademy.taskapi.packaging.entity.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackagingRepository extends JpaRepository<Packaging, Integer> {
}
