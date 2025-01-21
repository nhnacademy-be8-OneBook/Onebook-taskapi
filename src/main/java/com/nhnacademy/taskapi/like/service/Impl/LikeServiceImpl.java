package com.nhnacademy.taskapi.like.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.like.dto.LikeReponse;
import com.nhnacademy.taskapi.like.exception.LikeNotFoundException;
import com.nhnacademy.taskapi.like.repository.LikeRepository;
import com.nhnacademy.taskapi.like.service.LikeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
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
    public LikeReponse plusLike(long bookId, long memberId) {
        Book book = bookService.getBook(bookId);
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
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()-> new MemberNotFoundException("Member Not Found by " + memberId)
        );

        Like likes = new Like();
        likes.setBook(book);
        likes.setMember(member);
        likeRepository.save(likes);

        LikeReponse likeReponse = new LikeReponse(book.getBookId(), member.getId());
        return likeReponse;
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

    @Override
    public LikeReponse getLikeByBook(long bookId){
        Like like = likeRepository.findByBook_BookId(bookId);
        LikeReponse likeReponse = new LikeReponse(bookId, like.getMember().getId());
        return likeReponse;
    }

    @Override
    public LikeReponse getLikeByLoginId(String loginId){
        Like like = likeRepository.findByMember_LoginId(loginId);
        LikeReponse res = new LikeReponse(like.getMember().getId(), like.getBook().getBookId());
        return res;
    }
}
