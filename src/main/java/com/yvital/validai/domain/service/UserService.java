package com.yvital.validai.domain.service;

import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yvital.validai.domain.enums.UserRole;
import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public User createUser(User user) {
        String email = user.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }
        String hashSenha = passwordEncoder.encode(user.getPasswordHash());
        
        user.setEmail(email);
        user.setRole(UserRole.PARTICIPANT);
        user.setPasswordHash(hashSenha);

        return userRepository.save(user);
    }

    public User login(String email, String password){
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Credenciais Inválidas."));

        if (!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new RuntimeException("Credenciais Inválidas.");
        }
        return user;
    }

    public User updateUser(Long id, User dadosAtualizados){
        User usuarioExistente = findById(id);

        if (!usuarioExistente.getEmail().equals(dadosAtualizados.getEmail()) && userRepository.existsByEmail(dadosAtualizados.getEmail())){
            throw new RuntimeException("Este e-mail já está em uso.");
        }

        usuarioExistente.setFirstName(dadosAtualizados.getFirstName());
        usuarioExistente.setLastName(dadosAtualizados.getLastName());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());

        return userRepository.save(usuarioExistente);
    }

    public void deleteUser(Long id) {
        User usuarioExistente = findById(id);
        userRepository.delete(usuarioExistente);
    }

}
