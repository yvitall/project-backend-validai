package com.yvital.validai.api.mapper;

import org.springframework.stereotype.Component;

import com.yvital.validai.api.dto.UserCreateDTO;
import com.yvital.validai.api.dto.UserResponseDTO;
import com.yvital.validai.api.dto.UserUpdateDTO;
import com.yvital.validai.domain.model.User;

@Component
public class UserMapper {

    public User toEntity(UserCreateDTO dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword());

        return user;
    }

    public User toEntity(UserUpdateDTO dto){
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        return user;
    }
    public UserResponseDTO toResponse(User entity){
        UserResponseDTO response = new UserResponseDTO();

        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setEmail(entity.getEmail());
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());

        return response;
    }
}
