package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.dto.MemberResponseDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/members")
public class MemberController {

    private final MemberService memberService;

    // 전체 멤버 조회
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        List<Member> members = memberService.getAllMembers();
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        for (Member member : members) {
            memberResponseDtoList.add(MemberResponseDto.from(member));
        }

        return ResponseEntity.ok().body(memberResponseDtoList);
    }

    // 인조키(id)로 멤버 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable("id") Long memberId) {
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
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable("id") Long memberId, @RequestBody @Valid MemberModifyDto memberModifyDto) {
        Member member = memberService.modifyMember(memberId, memberModifyDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.from(member);

        return ResponseEntity.ok().body(memberResponseDto);
    }


    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") Long memberId) {
        memberService.removeMember(memberId);
        return ResponseEntity.noContent().build();
    }

    // 멤버 정보 리턴 for JWT
    @GetMapping("/jwt/{loginId}")
    public ResponseEntity<Member> getMemberForJWT(@PathVariable("loginId") String loginId) {
        Member member = memberService.getMemberByLoginId(loginId);
        return ResponseEntity.ok().body(member);
    }

}

