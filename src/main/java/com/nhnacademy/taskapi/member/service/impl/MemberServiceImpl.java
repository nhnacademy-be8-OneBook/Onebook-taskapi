package com.nhnacademy.taskapi.member.service.impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl {

    private final MemberRepository memberRepository;

    // 전체 멤버 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 인조키(id)로 멤버 조회
    public Member getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if(Objects.isNull(member)) {
            throw new NotFoundException("Member not found");
        }
        return
    }

    // 로그인 ID(loginId)로 멤버 조회
    public Member getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }



}
