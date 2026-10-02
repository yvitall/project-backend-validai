package com.yvital.validai.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yvital.validai.api.dto.LoginResponseDTO;
import com.yvital.validai.api.dto.UserCreateDTO;
import com.yvital.validai.api.dto.UserLoginDTO;
import com.yvital.validai.api.dto.UserResponseDTO;
import com.yvital.validai.api.dto.UserRoleUpdateDTO;
import com.yvital.validai.api.mapper.UserMapper;
import com.yvital.validai.domain.enums.UserRole;
import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.service.UserService;
import com.yvital.validai.infra.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1") // Ajustado para corresponder exatamente à documentação para o base path, ou usarei /users
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    // 1.1 Cadastro de Usuário
    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserCreateDTO request) {
        User userEntity = userMapper.toEntity(request);
        User savedUser = userService.createUser(userEntity);
        UserResponseDTO responseDTO = userMapper.toResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // 1.2 Login (Autenticação)
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO loginRequest) {
        User userAuth = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
        String token = tokenService.generateToken(userAuth);
        
        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(7200L) // 2 horas (em segundos)
                .id(userAuth.getId())
                .email(userAuth.getEmail())
                .role(userAuth.getRole())
                .build();
                
        return ResponseEntity.ok(responseDTO);
    }

    // 1.9 Listagem de Usuários (Admin)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam(required = false) UserRole role) {
        List<User> users = userService.findByRole(role);
        List<UserResponseDTO> responseDTOs = users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // 1.10 Atualização de Papel (Admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{userId}/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UserRoleUpdateDTO roleUpdate) {
        User updatedUser = userService.updateRole(userId, roleUpdate.getRole());
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    // 1.14 Minhas Inscrições (Resumo/Mock - Endpoint pertencente ao path de User)
    // Na próxima unidade, onde a entidade Registration for desenvolvida, isso retornará os dados reais.
    @GetMapping("/users/me/registrations")
    public ResponseEntity<List<Object>> getMyRegistrations() {
        // Implementação futura quando RegistrationDTO e RegistrationService forem criados.
        // O Subject do token JWT nos dará o email/id do usuário para buscar.
        return ResponseEntity.ok(List.of());
    }
}