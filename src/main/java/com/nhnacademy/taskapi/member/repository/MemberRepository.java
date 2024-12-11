package com.nhnacademy.taskapi.member.repository;

import com.nhnacademy.taskapi.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
