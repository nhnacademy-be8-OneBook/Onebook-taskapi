package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberLoginDto> login(@RequestBody MemberLoginDto memberLoginDto) {
        MemberLoginDto result =  memberService.validateLogin(memberLoginDto);
        return ResponseEntity.ok().body(result);
    }

}
