package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    Page<MemberResponseDto> getAllMembers(Pageable pageable);
    MemberResponseDto getMemberById(Long id);
    Member getMemberByLoginId(String loginId);
    boolean existsById(Long id);
    boolean existsByLoginId(String loginId);
    MemberResponseDto registerMember(MemberRegisterRequestDto memberRegisterRequestDto);
    MemberResponseDto modifyMember(Long memberId, MemberModifyRequestDto memberModifyRequestDto);
    void removeMember(Long memberId);
    void changeStatusToActivation(String loginId);
    void updateMemberLoginId(String loginId);
    MembershipCheckResponseDto validateMembership(Long memberId, MembershipCheckRequestDto membershipCheckRequestDto);
    MemberLoginDto validateLogin(MemberLoginDto memberLoginDto);
    String getLoginIdById(Long id);

}
