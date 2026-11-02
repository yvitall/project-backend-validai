package com.yvital.validai.core.security;

import com.yvital.validai.domain.model.User;
import com.yvital.validai.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component // Avisa ao Spring que ele deve gerenciar essa classe
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter { // Garante que o filtro rode apenas UMA VEZ por requisição

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Procura o cabeçalho "Authorization" na requisição
        var token = this.recoverToken(request);
        // 2. Passa o token na maquininha (TokenService). Se for válido, devolve o
        // email.
        var email = tokenService.verifyToken(token);
        if (token != null) {
            if (!email.isEmpty()) {
                // 3. Checa rápido no banco se o usuário dono do token ainda existe
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                // 4. Monta a "Ficha" do Spring: "Este usuário está logado e tem a ROLE (Cargo)
                // X"
                // O Spring exige o prefixo "ROLE_" para trabalhar bem com as permissões depois
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

                // 5. Salva essa ficha na memória temporária da requisição (SecurityContext)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. O segurança sai da frente e manda a requisição seguir o fluxo normal
        filterChain.doFilter(request, response);
    }

    // Método auxiliar limpo para extrair apenas o código JWT
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}