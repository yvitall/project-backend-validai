package com.yvital.validai.core.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yvital.validai.domain.enums.UserRole;
import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRole() == UserRole.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER")); // Admin também pode organizar
        } else if (user.getRole() == UserRole.ORGANIZER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities);
    }
}

// camada para: conversão de UserModel p/ UserDetails do Spring Sec