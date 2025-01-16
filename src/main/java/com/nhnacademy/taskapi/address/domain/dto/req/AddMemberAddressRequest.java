package com.nhnacademy.taskapi.address.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class AddMemberAddressRequest{

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[가-힣]*$\t")
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$\n")
    private String phoneNumber;

    @Size(max = 20)
    private String alias;

    @Size(max = 100)
    private String requestedTerm;

    @NotBlank
    @Pattern(regexp = "^[0-6][0-3]\\d{3}\n")
    private String zipCode;

    @NotBlank
    @Size(max = 100)
    private String roadNameAddress;

    @NotBlank
    @Size(max = 100)
    private String numberAddress;

    @NotBlank
    @Size(max = 100)
    private String notes;

    @NotBlank
    @Size(max = 100)
    private String detailAddress;

    @NotNull
    private boolean defaultLocation;
}
