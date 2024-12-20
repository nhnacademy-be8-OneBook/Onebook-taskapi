package com.nhnacademy.taskapi.address.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddMemberAddressRequest {

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
