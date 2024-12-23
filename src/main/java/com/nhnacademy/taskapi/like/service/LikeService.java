package com.nhnacademy.taskapi.like.service;

import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.member.domain.Member;

public interface LikeService {

    Like plusLike(LikePlusMinusDTO dto);

    void minusLike(LikePlusMinusDTO dto);
}
