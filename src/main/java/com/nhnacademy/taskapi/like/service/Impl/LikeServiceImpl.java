package com.nhnacademy.taskapi.like.service.Impl;

import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.repository.LikeRepository;
import com.nhnacademy.taskapi.like.service.LikeService;
import com.nhnacademy.taskapi.member.repository.MemberRepository;

public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private MemberRepository memberRepository;
    private BookRepository bookRepository;


    @Override
    public Like plusLike(String memberId, long bookId) {
        return null;
    }
}