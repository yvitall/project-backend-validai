package com.yvital.validai.core.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Tenta recuperar o token do cabeçalho da requisição
        var token = this.recoverToken(request);

        // 2. Se o token não for nulo, nós validamos
        if (token != null) {
            var login = tokenService.validateToken(token);

            // Se o login (subject do token) for válido
            if (login != null && !login.isEmpty()) {
                // 3. Busca o usuário usando o nosso CustomUserDetailsService
                UserDetails user = userDetailsService.loadUserByUsername(login);

                // 4. Cria o objeto de autenticação do Spring
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // 5. Coloca esse usuário no Contexto de Segurança.
                // A partir daqui, a requisição sabe quem é o usuário logado.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Manda a requisição seguir em frente (seja para um Controller ou para o
        // próximo filtro)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extrair o token
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}

// um filtro para cada requisição, antes da requisição chegar no controller
// essa camada faz uma verificação se o usuário está autenticado, se de acordo
// com o token
// dele, ele tem permissão para fazer essa request ou não