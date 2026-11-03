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

        String token = this.recoverToken(request);

        if (token != null) {
            String email = tokenService.verifyToken(token);

            if (!email.isEmpty()) {
                // 3. Checa rápido no banco se o usuário dono do token ainda existe
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}