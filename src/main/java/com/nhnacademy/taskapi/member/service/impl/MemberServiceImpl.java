package com.nhnacademy.taskapi.member.service.impl;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.service.GradeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.exception.MemberAlreadyExistsException;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final GradeService gradeService;

    // 회원 정보 저장 - 회원 가입
    @Override
    public Member registerMember(MemberRegisterDto memberRegisterDto) {
        if(existsByLoginId(memberRegisterDto.loginId())) {
            throw new MemberAlreadyExistsException("Already exists loginID");
        }

        Member member = new Member(
                gradeService.getDefaultGrade(),
                memberRegisterDto.name(),
                memberRegisterDto.loginId(),
                memberRegisterDto.password(),
                memberRegisterDto.dateOfBirth(),
                memberRegisterDto.gender(),
                memberRegisterDto.email(),
                memberRegisterDto.phoneNumber()
        );
        return memberRepository.save(member);
    }

    // 전체 멤버 조회
    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 인조키(id)로 멤버 조회
    @Override
    public Member getMemberById(String id) {
        if(!existsById(id)) {
            throw new MemberIllegalArgumentException("Member id is wrong");
        }

        Member member = memberRepository.findById(id).orElse(null);

        if(Objects.isNull(member)) {
            throw new MemberNotFoundException("Member not found by id");
        }
        return member;
    }

    // 로그인 ID(loginId)로 멤버 조회
//    @Override
//    public Member getMemberByLoginId(String loginId) {
//        if(!existsByLoginId(loginId)) {
//            throw new MemberIllegalArgumentException("Member loginId is wrong");
//        }
//
//        Member member = memberRepository.findByLoginId(loginId).orElse(null);
//
//        if(Objects.isNull(member)) {
//            throw new MemberNotFoundException("Member not found by loginId");
//        }
//        return member;
//    }

    // 중복 확인 - 회원 인조키(id)가 존재하는지 확인
    @Override
    public boolean existsById(String id) {
        return memberRepository.existsById(id);
    }

    // 중복 확인 - 회원 로그인 ID(loginId)가 존재하는지 확인
    @Override
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 로그인 - 회원인지 확인
    @Override
    public MemberLoginDto checkMemberLogin(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByLoginId(memberLoginDto.loginId()).orElse(null);

        if(Objects.isNull(member)) {
            throw new MemberNotFoundException("Member not found by loginId");
        }

        if(!member.getPassword().equals(memberLoginDto.password())) {
            throw new MemberIllegalArgumentException("Wrong password");
        }

        return new MemberLoginDto(member.getLoginId(), member.getPassword());
    }

    // 인증된 회원을 HTTP 요청의 Authorization 헤더에서 확인
    public Member getAuthenticatedMember(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더에서 "Basic loginId:password" 형식의 데이터를 확인
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new MemberNotFoundException("Invalid or missing Authorization header");
        }

        // "Basic "을 제거하고 base64 디코딩하여 사용자 정보 얻기
        String encodedCredentials = authorizationHeader.substring(6);
        String decodedCredentials = new String(java.util.Base64.getDecoder().decode(encodedCredentials));
        String[] credentials = decodedCredentials.split(":");
      
        if (credentials.length != 2) {
            throw new MemberIllegalArgumentException("Invalid Authorization header format");
        }

        String loginId = credentials[0];
        String password = credentials[1];

        // 로그인 ID로 회원을 찾음
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new MemberNotFoundException("Member not found by loginId"));

        // 비밀번호 확인
        if (!member.getPassword().equals(password)) {
            throw new MemberIllegalArgumentException("Incorrect password");
        }
        return member;
    }

  
    // 회원 정보 수정
}