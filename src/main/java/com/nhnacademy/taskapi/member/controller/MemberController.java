package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.JwtMemberDto;
import com.nhnacademy.taskapi.member.dto.MemberModifyRequestDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterRequestDto;
import com.nhnacademy.taskapi.member.dto.MemberResponseDto;
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
     * @return
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

    // 회원 상태 변경 -  'ACTIVE', 'SUSPENDED'.
    @GetMapping("/status/{status}")
    public ResponseEntity<String> modifyMemberStatus(@RequestHeader("X-MEMBER-ID") Long memberId, @PathVariable("status") String status) {
        memberService.changeStatusToActivation(memberId, status);
        return ResponseEntity.noContent().build();
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

