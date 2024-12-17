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

    /**
     *
     * @return 로그인 시도하려는 아이디랑 패스워드랑 같으면 true, 아니면 false
     *
     * @author 수정자 문영호
     */
    @PostMapping("/task/auth/login")
    public ResponseEntity<Boolean> login(@RequestBody MemberLoginDto memberLoginDto) {

        MemberLoginDto result =  memberService.validateLogin(memberLoginDto);
        return ResponseEntity.ok().body( result != null );
    }

}
