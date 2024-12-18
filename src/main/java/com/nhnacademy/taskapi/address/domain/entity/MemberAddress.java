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
    @GeneratedValue
    @Column(name = "member_address_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    private String name;
    @Setter
    private String phoneNumber;
    @Setter
    private String alias;
    @Setter
    private String requestedTerm;
    @Setter
    private String zipCode;
    @Setter
    private String roadNameAddress;
    @Setter
    private String numberAddress;
    @Setter
    private String notes;
    @Setter
    private String detailAddress;
    @Setter
    private Integer defaultLocation;

}
