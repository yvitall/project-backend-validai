package com.yvital.validai.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UserCreateDTO {

@Size(min = 3, max = 50)
@NotBlank 
private String firstName;

@Size(min = 3, max = 50)
@NotBlank 
private String lastName;

@Email
@NotBlank 
private String email;

@NotBlank
@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*]).+$", 
        message = "A senha deve conter pelo menos uma letra maiúscula e um caractere especial"
    )
private String password;
}
