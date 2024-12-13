package com.nhnacademy.taskapi.member.domain;

import com.nhnacademy.taskapi.grade.domain.Grade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="members", uniqueConstraints = {
        @UniqueConstraint(
                name="UNIQUE_loginId_email_phoneNumber",
                columnNames = {"login_id", "email", "phone_number"}
        )
})
public class Member {

    // 회원 상태 - 활성화, 비활성화(휴면), 삭제, 정지 / default: INACTIVE
    public enum Status {
        ACTIVE, INACTIVE, DELETED, SUSPENDED
    }

    // 회원 성별 - 남,여
    public enum Gender {
        M, F
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="member_id")
    private String id;

    @JoinColumn(name="grade_id")
    @Setter
    @ManyToOne
    private Grade gradeId;

    @NotBlank
    private String name;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private LocalDate dateOfBirth;

    @NotBlank
    private Gender gender;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private LocalDateTime lastLoginAt;

    @Setter
    @NotBlank
    private Status status; // default: INACTIVE

    // 로그인 기록, 회원 상태가 없는 회원 -> 회원 가입
    public Member(Grade gradeId, String name, String loginId, String password, LocalDate dateOfBirth, Gender gender,String email, String phoneNumber) {
        this.gradeId = gradeId;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastLoginAt = null;
        this.status = Status.INACTIVE; // default
    }

    // 로그인 기록이 있는 회원.
    public Member(Grade gradeId, String name, String loginId, String password, LocalDate dateOfBirth, Gender gender,String email, String phoneNumber, LocalDateTime lastLoginAt, Status status) {
        this.gradeId = gradeId;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastLoginAt = lastLoginAt;
        this.status = status;
    }

}
