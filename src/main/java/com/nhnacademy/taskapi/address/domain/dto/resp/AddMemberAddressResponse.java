package com.nhnacademy.taskapi.address.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddMemberAddressResponse {
    private String name;
    private String phoneNumber;
    private String alias;
    private String requestedTerm;
    private String zipCode;
    private String roadNameAddress;
    private String numberAddress;
    private String notes;
    private String detailAddress;
    private Integer defaultLocation;
}
