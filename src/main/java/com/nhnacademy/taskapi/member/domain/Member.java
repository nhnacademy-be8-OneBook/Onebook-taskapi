package com.nhnacademy.taskapi.member.domain;

import com.nhnacademy.taskapi.grade.domain.Grade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name="members")
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
    @GeneratedValue(strategy = GenerationType.UUID) // UUID v4
    @Column(name="member_id")
    private String id;

    @ManyToOne
    @JoinColumn(name="grade_id", nullable=false)
    @Setter
    private Grade grade;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name="login_id", unique=true)
    private String loginId;

    @NotBlank
    private String password;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    @NotBlank
    @Column(name="email", unique=true)
    private String email;

    @NotBlank
    @Column(name="phone_number", unique = true)
    private String phoneNumber;

    private LocalDateTime lastLoginAt;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // default: INACTIVE

    // 로그인 기록, 회원 상태가 없는 회원 -> 회원 가입
    public Member(Grade grade, String name, String loginId, String password, LocalDate dateOfBirth, Gender gender, String email, String phoneNumber) {
        this.grade = grade;
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
    public Member(Grade grade, String name, String loginId, String password, LocalDate dateOfBirth, Gender gender,String email, String phoneNumber, LocalDateTime lastLoginAt, Status status) {
        this.grade = grade;
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
