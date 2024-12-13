package com.nhnacademy.taskapi.point.jpa;

import com.nhnacademy.taskapi.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPointRepository extends JpaRepository<Point, Long> {

    // memberId로 포인트를 찾는 메서드
    Optional<Point> findByMember_MemberId(Long memberId);

    // userId로 포인트를 찾는 메서드
    Optional<Point> findByUser_UserId(Long userId);
}