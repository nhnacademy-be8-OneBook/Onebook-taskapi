package com.nhnacademy.taskapi.address.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DeleteMemberAddressResponse {

    private final Long id;

    private DeleteMemberAddressResponse(Long id) {
        this.id = id;
    }

    public static DeleteMemberAddressResponse changeEntityToDto(Long id){
        return new DeleteMemberAddressResponse(id);
    }
}
