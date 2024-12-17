package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(String id);
//    Member getMemberByLoginId(String loginId);
    boolean existsById(String id);
    boolean existsByLoginId(String loginId);
    MemberLoginDto checkMemberLogin(MemberLoginDto memberLoginDto);
    Member registerMember(MemberRegisterDto memberRegisterDto);
    Member getAuthenticatedMember(HttpServletRequest request); // 인증된 사용자 정보를 가져오는 메서드
}
