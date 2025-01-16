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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    /**
     * 수정일: 2024/12/31
     * 수정자: 김주혁
     * 수정 내용: memberRepository 추가.
     */
    private MemberRepository memberRepository;
    private MemberService memberService;
    private BookService bookService;


    @Transactional
    @Override
    public Like plusLike(LikePlusMinusDTO dto) {
        Book book = bookService.getBook(dto.getBookId());
        if(Objects.isNull(book)){
            throw new BookNotFoundException("Book Not Found");
        }
        /**
         * 수정일: 2024/12/31
         * 수정자: 김주혁
         * 수정 내용
         *  1. 기존 내용 주석 처리
         *  2. memberService.getMemberById() -> memberRepository.findById()
         *  3. MemberNotFoundException 를 repo에서 가져오지 못하면 바로 던지게 바꿈.
         */
//        Member member = memberService.getMemberById(dto.getMemberId());
//        if(Objects.isNull(member)){
//            throw new MemberNotFoundException("Member Not Found");
//        }
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(
                ()-> new MemberNotFoundException("Member Not Found by " + dto.getMemberId())
        );

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
