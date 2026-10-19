package com.yvital.validai.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Avisa ao Spring: "Deixa que eu dito as regras de segurança agora"
public class SecurityConfig {

    // 1. O LIQUIDIFICADOR DE SENHAS
    // Quando você for salvar um usuário ou checar a senha no login,
    // o Spring vai usar essa ferramenta para embaralhar/desembaralhar.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. O LIVRO DE REGRAS BÁSICO
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desliga a proteção de formulários web (CSRF), pois nossa API não tem telas
                // HTML próprias
                .csrf(csrf -> csrf.disable())

                // Ativa a "Amnésia" do servidor. Ele não vai lembrar quem está logado
                // (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Aqui trancamos tudo. "Qualquer requisição precisa estar autenticada"
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated());

        return http.build();
    }
}