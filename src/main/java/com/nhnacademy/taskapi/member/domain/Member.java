package com.nhnacademy.taskapi.member.domain;

import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.grade.domain.Grade;
import jakarta.persistence.*;
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
@Table(name="member", uniqueConstraints = {
        @UniqueConstraint(
                name="LOGINID_PhoneNumber_UNIQUE",
                columnNames = {"login_id", "phone_number"}
        )
})
public class Member {

    // 활성화, 비활성화(휴면), 삭제, 정지
    public enum Status {
        ACTIVE, INACTIVE, DELETED, SUSPENDED
    }

    public enum Gender {
        M, F
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private long id;

    @JoinColumn(name="customer_id")
    @OneToOne
    private Customer customerId;

    @JoinColumn(name="grade_id")
    @ManyToOne
    private Grade gradeId;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private LocalDate dateOfBirth;

    @NotBlank
    private Gender gender;

    @NotBlank
    private String phoneNumber;

    private LocalDateTime lastLoginAt;

    @Setter
    @NotBlank
    private Status status; // default: ACTIVE

    // 로그인 기록이 없는 회원.
    public Member(Customer customerId, Grade gradeId, String loginId, String password, LocalDate dateOfBirth, Gender gender, String phoneNumber) {
        this.customerId = customerId;
        this.gradeId = gradeId;
        this.loginId = loginId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.lastLoginAt = null;
        this.status = Status.ACTIVE;
    }

    // 로그인 기록이 있는 회원.
    public Member(Customer customerId, Grade gradeId, String loginId, String password, LocalDate dateOfBirth, Gender gender, String phoneNumber, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.gradeId = gradeId;
        this.loginId = loginId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.lastLoginAt = lastLoginAt;
        this.status = Status.ACTIVE;
    }

}