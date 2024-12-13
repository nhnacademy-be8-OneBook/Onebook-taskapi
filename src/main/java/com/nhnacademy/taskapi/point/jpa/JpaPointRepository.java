package com.nhnacademy.taskapi.point.jpa;

import com.nhnacademy.taskapi.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByMember_MemberId(Long memberId);
}