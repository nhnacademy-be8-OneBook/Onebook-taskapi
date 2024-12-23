package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.JwtMemberDto;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.dto.MemberResponseDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/members")
public class MemberController {

    private final MemberService memberService;

    // 전체 멤버 조회
    @GetMapping("/list")
    public ResponseEntity<Page<MemberResponseDto>> getMembers(@RequestParam(value="page", defaultValue = "0") int page) {
        Page<Member> memberList = memberService.getAllMembers(page);

        Page<MemberResponseDto> memberResponseDtoPage = memberList.map(MemberResponseDto::from);

        return ResponseEntity.ok().body(memberResponseDtoPage);
    }

    // request header의 인조키(id)로 멤버 조회
    @GetMapping
    public ResponseEntity<MemberResponseDto> getMemberById(@RequestHeader("X-MEMBER-ID") Long memberId) {
       Member member = memberService.getMemberById(memberId);
       MemberResponseDto memberResponseDto = MemberResponseDto.from(member);

       return ResponseEntity.ok().body(memberResponseDto);
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody @Valid MemberRegisterDto memberRegisterDto) {
        Member member = memberService.registerMember(memberRegisterDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.from(member);

        return ResponseEntity.ok().body(memberResponseDto);
    }

    // 멤버 정보 수정
    @PutMapping
    public ResponseEntity<MemberResponseDto> updateMember(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody @Valid MemberModifyDto memberModifyDto) {
        Member member = memberService.modifyMember(memberId, memberModifyDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.from(member);

        return ResponseEntity.ok().body(memberResponseDto);
    }


    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.
    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestHeader("X-MEMBER-ID") Long memberId) {
        memberService.removeMember(memberId);
        return ResponseEntity.noContent().build();
    }


    // 멤버 정보 리턴 for JWT
    @GetMapping("/jwt/{loginId}")
    public ResponseEntity<JwtMemberDto> getMemberForJWT(@PathVariable("loginId") String loginId) {
//        Member member = memberService.getMemberByLoginId(loginId);
        JwtMemberDto jwtMemberDto = new JwtMemberDto();
        // TODO - jwt
        // jwtMemberDto에 필요한것들 적기
//        jwtMemberDto.setId(member.getId());
//        jwtMemberDto.setLoginId(member.getLoginId());
        jwtMemberDto.setLoginId("test");

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

