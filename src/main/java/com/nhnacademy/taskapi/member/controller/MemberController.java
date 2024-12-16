package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/members")
    public ResponseEntity<Member> postMember(@RequestBody @Valid MemberRegisterDto memberRegisterDto) {
        Member member = memberService.registerMember(memberRegisterDto);
        return ResponseEntity.ok().body(member);
    }

    // 전체 멤버 조회
    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok().body(members);
    }

    // 인조키(id)로 멤버 조회
    @GetMapping("/members/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable("memberId") String memberId) {
       Member member = memberService.getMemberById(memberId);
       return ResponseEntity.ok().body(member);
    }

    // 로그인 ID(loginId)로 멤버 조회
//    @GetMapping("/members/login/{loginId}")
//    public ResponseEntity<Member> getMemberByLoginId(@PathVariable("loginId") String loginId) {
//        Member member = memberService.getMemberByLoginId(loginId);
//        return ResponseEntity.ok().body(member);
//    }

    // 멤버 정보 수정


    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberLoginDto> postLogin(@RequestBody MemberLoginDto memberLoginDto) {
        MemberLoginDto result =  memberService.checkMemberLogin(memberLoginDto);
        return ResponseEntity.ok().body(result);
    }


}
