package com.nhnacademy.taskapi.like.repository;

import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikeReponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByBook_BookIdAndMember_Id(Long bookId, Long memberId);
    Like findByBook_BookId(Long bookId);
    Like findByMember_LoginId(String loginId);
}
