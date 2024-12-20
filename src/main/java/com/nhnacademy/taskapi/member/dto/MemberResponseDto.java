package com.nhnacademy.taskapi.member.dto;

import com.nhnacademy.taskapi.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberResponseDto(

        /**
         * Grade ID -> 1: REGULAR, 2: ROYAL, 3: GOLD, 4: PLATINUM
         * Role ID -> 1: MEMBER, 2: ADMIN
         */

        Integer gradeId,
        Integer roleId,
        String name,
        String loginId,
        LocalDate dateOfBirth,
        String gender,
        String email,
        String phoneNumber,
        String status,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt

) {
    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getGrade().getId(),
                member.getRole().getId(),
                member.getName(),
                member.getLoginId(),
                member.getDateOfBirth(),
                String.valueOf(member.getGender()),
                maskEmail(member.getEmail()),
                maskPhoneNumber(member.getPhoneNumber()),
                String.valueOf(member.getStatus()),
                member.getCreatedAt(),
                member.getLastLoginAt()
        );
    }

    private static String makeMask(int number) {
        StringBuilder mask = new StringBuilder();

        for(int i = 0; i<number; i++) {
            mask.append("*");
        }

        return mask.toString();
    }

    // 이메일 마스킹 처리 (예: helloworld@gmail.com -> hel******@gmail.com)
    private static String maskEmail(String email) {
        String[] split = email.split("@");

        String localPart = split[0];
        localPart = localPart.substring(0, 3) + makeMask(split[0].length() - 3);

        String domainPart = split[1];

        return localPart + "@" + domainPart;
    }

    // 전화번호 마스킹 처리 (예: 010-1111-1111 -> 010-1****-1****)
    private static String maskPhoneNumber(String phoneNumber) {
        String[] split = phoneNumber.split("-");

        String middlePart = split[1];
        middlePart = middlePart.charAt(0) + makeMask(middlePart.length() - 1);

        String endPart = split[2];
        endPart = endPart.charAt(0) + makeMask(endPart.length() - 1);

        return split[0] + "-" + middlePart + "-" + endPart;
    }

}
