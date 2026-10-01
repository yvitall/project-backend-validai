package com.yvital.validai.api.dto;

import java.time.LocalDateTime;

import com.yvital.validai.domain.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private UserRole role;

    private LocalDateTime createdAt;
}
