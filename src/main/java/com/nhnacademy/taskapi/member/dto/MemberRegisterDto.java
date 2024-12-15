package com.nhnacademy.taskapi.member.dto;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberRegisterDto(
        @NotBlank(message="회원 이름은 필수 입력 항목입니다.") String name,
        @NotBlank(message="로그인 ID는 필수 입력 항목입니다.") String loginId,
        @NotBlank(message="로그인 PW는 필수 입력 항목입니다.") String password,
        @NotNull(message="생년월일은 필수 입력 항목입니다.") LocalDate dateOfBirth,
        @NotNull(message="성별은 필수 입력 항목입니다.") Member.Gender gender,
        @Email(message = "이메일 형식으로 작성해야 합니다.") String email,
        @NotBlank(message="전화번호는 필수 입력 항목입니다.") String phoneNumber
) {}



