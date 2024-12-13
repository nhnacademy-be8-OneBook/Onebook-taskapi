package com.nhnacademy.taskapi.member.repository;

import com.nhnacademy.taskapi.member.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, String> {
    Optional<Members> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
