package com.nhnacademy.taskapi.address.domain.dto.resp;

import com.nhnacademy.taskapi.address.domain.entity.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetMemberAddressResponse {

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

    private GetMemberAddressResponse(String name, String phoneNumber, String alias, String requestedTerm, String zipCode,
                                     String roadNameAddress, String numberAddress, String notes, String detailAddress,
                                     Integer defaultLocation) {
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

    public static GetMemberAddressResponse changeEntityToDto(MemberAddress memberAddress) {
        return new GetMemberAddressResponse(
                memberAddress.getName(), memberAddress.getPhoneNumber(), memberAddress.getAlias(), memberAddress.getRequestedTerm()
                , memberAddress.getZipCode(), memberAddress.getRoadNameAddress(), memberAddress.getNumberAddress(),
                memberAddress.getNotes(), memberAddress.getDetailAddress(), memberAddress.getDefaultLocation());
    }

}
