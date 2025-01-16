package com.nhnacademy.taskapi.address.domain.dto.req;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeleteMemberAddressRequest {

    @NotNull
    @PositiveOrZero
    private Long id;
}
