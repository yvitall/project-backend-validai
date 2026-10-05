package com.yvital.validai.api.dto;

import com.yvital.validai.domain.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private Long id;
    private String email;
    private UserRole role;
}
