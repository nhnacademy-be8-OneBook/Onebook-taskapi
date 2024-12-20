package com.nhnacademy.taskapi.address.domain.dto.req;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class UpdateMemberAddressRequest
{
    @NotNull
    @PositiveOrZero
    private Long id;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[가-힣]*$\t")
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$\n")
    private String phoneNumber;

    @NotBlank
    @Size(max = 20)
    private String alias;

    @NotBlank
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
