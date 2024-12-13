package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Members;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;

import java.util.List;

public interface MemberService {
    List<Members> getAllMembers();
    Members getMemberById(String id);
    Members getMemberByLoginId(String loginId);
    boolean existsById(String id);
    boolean existsByLoginId(String loginId);
    boolean checkMember(MemberLoginDto memberLoginDto);
    Members registerMember(MemberRegisterDto memberRegisterDto);
}
