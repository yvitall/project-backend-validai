package com.yvital.validai.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yvital.validai.api.dto.request.UserLoginDTO;
import com.yvital.validai.api.dto.response.LoginResponseDTO;
import com.yvital.validai.core.security.TokenService;
import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    // 1.2 Login (Autenticação)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO loginRequest) {
        User userAuth = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        String token = tokenService.generateToken(userAuth);

        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(7200L)
                .id(userAuth.getId())
                .email(userAuth.getEmail())
                .role(userAuth.getRole())
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
