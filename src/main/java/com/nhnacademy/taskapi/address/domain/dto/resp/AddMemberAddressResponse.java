package com.nhnacademy.taskapi.address.domain.dto.resp;

import com.nhnacademy.taskapi.address.domain.entity.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
    private boolean defaultLocation;

    public static AddMemberAddressResponse changeEntityToDto(MemberAddress memberAddress) {
        return new AddMemberAddressResponse(
                memberAddress.getName(), memberAddress.getPhoneNumber(), memberAddress.getAlias(), memberAddress.getRequestedTerm()
                , memberAddress.getZipCode(), memberAddress.getRoadNameAddress(), memberAddress.getNumberAddress(),
                memberAddress.getNotes(), memberAddress.getDetailAddress(), memberAddress.isDefaultLocation());
    }
}