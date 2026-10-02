package com.yvital.validai.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yvital.validai.api.dto.UserCreateDTO;
import com.yvital.validai.api.dto.UserLoginDTO;
import com.yvital.validai.api.dto.UserResponseDTO;
import com.yvital.validai.api.mapper.UserMapper;
import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserCreateDTO request) {
        
        User userEntity = userMapper.toEntity(request);
        
        User savedUser = userService.createUser(userEntity);
        
        UserResponseDTO responseDTO = userMapper.toResponse(savedUser);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody UserLoginDTO loginRequest){
        User userAuth = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        UserResponseDTO responseDTO = userMapper.toResponse(userAuth);
        return ResponseEntity.ok(responseDTO);
    }
}