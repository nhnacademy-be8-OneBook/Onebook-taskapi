package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.domain.Members;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> postLogin(@RequestBody MemberLoginDto memberLoginDto) {
        boolean result = memberService.checkMember(memberLoginDto); // 로그인 성공
            return ResponseEntity.ok().body(result);
    }

    // 회원가입
    @PostMapping("/member")
    public ResponseEntity<Members> postMember(@RequestBody @Valid MemberRegisterDto memberRegisterDto) {
       Members member = memberService.registerMember(memberRegisterDto);
       return ResponseEntity.ok().body(member);
    }
}
