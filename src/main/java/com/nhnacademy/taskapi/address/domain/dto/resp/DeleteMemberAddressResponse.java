package com.nhnacademy.taskapi.address.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMemberAddressResponse {

    private final Long id;

    public static DeleteMemberAddressResponse changeEntityToDto(Long id){
        return new DeleteMemberAddressResponse(id);
    }
}
