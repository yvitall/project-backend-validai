package com.yvital.validai.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private Long id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 50)
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

}
