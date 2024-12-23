package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberService {
    Page<Member> getAllMembers(int page);
    Member getMemberById(Long id);
    Member getMemberByLoginId(String loginId);
    boolean existsById(Long id);
    boolean existsByLoginId(String loginId);
    Member registerMember(MemberRegisterDto memberRegisterDto);
    Member modifyMember(Long memberId, MemberModifyDto memberModifyDto);
    void removeMember(Long memberId);
    MemberLoginDto validateLogin(MemberLoginDto memberLoginDto);
    String getLoginIdById(Long id);

}
