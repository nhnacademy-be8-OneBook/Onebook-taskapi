package com.nhnacademy.taskapi.like.service;

import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.member.domain.Member;

public interface LikeService {

    Like plusLike(String memberId, long bookId);
}
