package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(String id);
//    Member getMemberByLoginId(String loginId);
    boolean existsById(String id);
    boolean existsByLoginId(String loginId);
    MemberLoginDto checkMemberLogin(MemberLoginDto memberLoginDto);
    Member registerMember(MemberRegisterDto memberRegisterDto);

}
