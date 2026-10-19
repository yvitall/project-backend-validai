package com.yvital.validai.api.dto;

import com.yvital.validai.domain.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateDTO {
    @NotNull(message = "O papel (role) não pode ser nulo")
    private UserRole role;
}
