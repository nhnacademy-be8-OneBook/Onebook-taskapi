package com.nhnacademy.taskapi.address.domain.dto.resp;

import com.nhnacademy.taskapi.address.domain.entity.MemberAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    private AddMemberAddressResponse(String name, String phoneNumber, String alias, String requestedTerm, String zipCode,
                                     String roadNameAddress, String numberAddress, String notes, String detailAddress,
                                     boolean defaultLocation) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.alias = alias;
        this.requestedTerm = requestedTerm;
        this.zipCode = zipCode;
        this.roadNameAddress = roadNameAddress;
        this.numberAddress = numberAddress;
        this.notes = notes;
        this.detailAddress = detailAddress;
        this.defaultLocation = defaultLocation;
    }

    public static AddMemberAddressResponse changeEntityToDto(MemberAddress memberAddress) {
        return new AddMemberAddressResponse(
                memberAddress.getName(), memberAddress.getPhoneNumber(), memberAddress.getAlias(), memberAddress.getRequestedTerm()
                , memberAddress.getZipCode(), memberAddress.getRoadNameAddress(), memberAddress.getNumberAddress(),
                memberAddress.getNotes(), memberAddress.getDetailAddress(), memberAddress.isDefaultLocation());
    }
}