package com.nhnacademy.taskapi.member.domain;

import com.nhnacademy.taskapi.customer.domain.Customer;
import com.nhnacademy.taskapi.grade.domain.Grade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

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
    private String phoneNumber;

    @NotBlank
    private LocalDate dateOfBirth;



}
