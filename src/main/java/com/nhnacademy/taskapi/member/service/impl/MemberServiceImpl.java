package com.nhnacademy.taskapi.member.service.impl;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.LoginDto;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
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
            throw new NotFoundException("Member not found by id");
        }
        return member;
    }

    // 로그인 ID(loginId)로 멤버 조회
    public Member getMemberByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElse(null);
        if(Objects.isNull(member)) {
            throw new MemberNotFoundException("Member not found by loginId");
        }
        return member;
    }

    // 로그인 ID(loginId)가 존재하는지 확인
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 로그인 ID, PW - 회원인지 확인
    public boolean checkMember(LoginDto loginDto) {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId()).orElse(null);
        if(Objects.isNull(member)) {
            throw new MemberNotFoundException("Member not found by loginId");
        }
        return member.getPassword().equals(loginDto.getPassword());
    }

    // 회원 가입
    public Member registerMember(Member member) {

    }



}
