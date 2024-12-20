package com.nhnacademy.taskapi.address.domain.entity;

import com.nhnacademy.taskapi.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_address_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @Column(nullable = false ,length = 10)
    private String name;

    @Setter
    @Column(nullable = false, length = 30)
    private String phoneNumber;

    @Setter
    @Column(length = 20)
    private String alias;

    @Setter
    @Column(length = 100)
    private String requestedTerm;

    @Setter
    @Column(nullable = false,length = 10)
    private String zipCode;

    @Setter
    @Column(nullable = false,length = 100)
    private String roadNameAddress;

    @Setter
    @Column(nullable = false,length = 100)
    private String numberAddress;

    @Setter
    @Column(length = 100)
    private String notes;

    @Setter
    @Column(length = 100)
    private String detailAddress;

    @Setter
    @Column(nullable = false)
    private Integer defaultLocation;

}
