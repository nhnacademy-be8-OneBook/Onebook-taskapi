package com.nhnacademy.taskapi.address.domain.dto.resp;

import com.nhnacademy.taskapi.address.domain.entity.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateMemberAddressResponse {
    private Long id;
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

    public static UpdateMemberAddressResponse changeEntityToDto(MemberAddress memberAddress) {
        return new UpdateMemberAddressResponse(
                memberAddress.getId(),memberAddress.getName(), memberAddress.getPhoneNumber(), memberAddress.getAlias(),
                memberAddress.getRequestedTerm(), memberAddress.getZipCode(), memberAddress.getRoadNameAddress(),
                memberAddress.getNumberAddress(), memberAddress.getNotes(), memberAddress.getDetailAddress(),
                memberAddress.isDefaultLocation());
    }
}
