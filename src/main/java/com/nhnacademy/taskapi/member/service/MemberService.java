package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    boolean existsById(Long id);
    boolean existsByLoginId(String loginId);
    Member registerMember(MemberRegisterDto memberRegisterDto);
    Member modifyMember(Long memberId, MemberModifyDto memberModifyDto);
    void removeMember(Long memberId);
    MemberLoginDto validateLogin(MemberLoginDto memberLoginDto);
}
