package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.grade.dto.GradeResponseDto;
import com.nhnacademy.taskapi.grade.service.GradeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.*;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/members")
public class MemberController {

    private final MemberService memberService;
    private final GradeService gradeService;

    // 전체 멤버 조회
    @GetMapping("/list")
    public ResponseEntity<Page<MemberResponseDto>> getMembers(Pageable pageable) {
        Page<MemberResponseDto> memberResponseDtoPage = memberService.getAllMembers(pageable);
        return ResponseEntity.ok().body(memberResponseDtoPage);
    }

    /**
     * Auth 서버에서 사용.
     * memberID로 member 정보 return.
     * @param memberId
     * @return MemberResponseDto
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberByParameterId(@PathVariable("memberId") Long memberId) {
        MemberResponseDto result = memberService.getMemberById(memberId);
        return ResponseEntity.ok(result);
    }

    // request header의 인조키(id)로 멤버 조회
    @GetMapping
    public ResponseEntity<MemberResponseDto> getMemberById(@RequestHeader("X-MEMBER-ID") Long memberId) {
        MemberResponseDto memberResponseDto = memberService.getMemberById(memberId);
       return ResponseEntity.ok().body(memberResponseDto);
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody @Valid MemberRegisterRequestDto memberRegisterRequestDto) {
        MemberResponseDto memberResponseDto = memberService.registerMember(memberRegisterRequestDto);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    // 멤버 정보 수정
    @PutMapping
    public ResponseEntity<MemberResponseDto> updateMember(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody @Valid MemberModifyRequestDto memberModifyRequestDto) {
        MemberResponseDto memberResponseDto = memberService.modifyMember(memberId, memberModifyRequestDto);
        return ResponseEntity.ok().body(memberResponseDto);
    }


    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.
    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestHeader("X-MEMBER-ID") Long memberId) {
        memberService.removeMember(memberId);
        return ResponseEntity.noContent().build();
    }

    // 회원 상태 변경 -  'ACTIVE'
    @GetMapping("/status/{loginId}")
    public ResponseEntity<String> modifyMemberStatus(@PathVariable("loginId") String loginId) {
        memberService.changeStatusToActivation(loginId);
        return ResponseEntity.noContent().build();
    }

    // 회원 등급 조회.
    @GetMapping("/grade")
    public ResponseEntity<GradeResponseDto> getGrade(@RequestHeader("X-MEMBER-ID") Long memberId) {
       MemberResponseDto memberResponseDto = memberService.getMemberById(memberId);
       GradeResponseDto result = gradeService.getGradeByName(memberResponseDto.grade());
       return ResponseEntity.ok(result);
    }

    // 회원 여부 조회.
    @PostMapping("/membership")
    public ResponseEntity<MembershipCheckResponseDto> checkMembership(
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @RequestBody MembershipCheckRequestDto membershipCheckRequestDto) {
        MembershipCheckResponseDto membershipCheckResponseDto = memberService.validateMembership(memberId, membershipCheckRequestDto);
        return ResponseEntity.ok(membershipCheckResponseDto);
    }

    /**
     * Auth api 에서 사용.
     * 멤버 정보 리턴 for JWT
     */
    @GetMapping("/jwt/{loginId}")
    public ResponseEntity<JwtMemberDto> getMemberForJWT(@PathVariable("loginId") String loginId) {
        Member member = memberService.getMemberByLoginId(loginId);
        JwtMemberDto jwtMemberDto = new JwtMemberDto();

        jwtMemberDto.setId(member.getId());
        jwtMemberDto.setLoginId(member.getLoginId());
        jwtMemberDto.setRole(member.getRole().getName());

        return ResponseEntity.ok().body(jwtMemberDto);
    }

    /**
     * 회원 ID로 loginId 가져오기.
     */
    @GetMapping("/loginId")
    public ResponseEntity<String> getMemberIdByLoginId(@RequestHeader("X-MEMBER-ID") Long memberId) {
        String loginId = memberService.getLoginIdById(memberId);
        return ResponseEntity.ok().body(loginId);
    }

    /**
     * 테스트용 member 다 가져오기.
     */
    @GetMapping("/test/{loginId}")
    public Member getMemberForTest(@PathVariable("loginId") String loginId) {
        return memberService.getMemberByLoginId(loginId);
    }

}

