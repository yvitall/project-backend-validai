package com.yvital.validai.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder PasswordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }
        return userRepository.save(user);
    }

}
