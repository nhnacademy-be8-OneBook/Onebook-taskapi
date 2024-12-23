package com.nhnacademy.taskapi.like.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.like.exception.LikeNotFoundException;
import com.nhnacademy.taskapi.like.repository.LikeRepository;
import com.nhnacademy.taskapi.like.service.LikeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private MemberService memberService;
    private BookService bookService;


    @Transactional
    @Override
    public Like plusLike(LikePlusMinusDTO dto) {
        Book book = bookService.getBook(dto.getBookId());
        if(Objects.isNull(book)){
            throw new BookNotFoundException("Book Not Found");
        }
        Member member = memberService.getMemberById(dto.getMemberId());
        if(Objects.isNull(member)){
            throw new MemberNotFoundException("Member Not Found");
        }

        Like likes = new Like();
        likes.setBook(book);
        likes.setMember(member);
        return likeRepository.save(likes);
    }

    @Transactional
    @Override
    public void minusLike(LikePlusMinusDTO dto){

        Like likes = likeRepository.findByBook_BookIdAndMember_Id(dto.getBookId(), dto.getMemberId());

        if(Objects.isNull(likes)){
            throw new LikeNotFoundException("Like Not Found");
        }


        likeRepository.delete(likes);


    }
}
