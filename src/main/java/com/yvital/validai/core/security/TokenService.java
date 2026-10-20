package com.yvital.validai.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yvital.validai.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret:${SECRET_KEY_TOKEN}")
    private String secret;

    public String generateToken(User user) {
        try {
            // o hmac256 criptografa a secret key
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("validai-api") // Quem emitiu
                    .withSubject(user.getEmail()) // O dono do token
                    .withClaim("role", user.getRole().name()) // Pendura a role (Ex: ADMIN)
                    .withClaim("id", user.getId()) // Pendura o ID do usuário
                    .withExpiresAt(genExpirationDate()) // Expira em 2h
                    .sign(algorithm); // Assina matematicamente
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("validai-api")
                    .build()
                    .verify(token) // Checa se a assinatura bate e se não expirou
                    .getSubject(); // Devolve o e-mail se estiver tudo OK
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se for falso, expirado ou alterado
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}