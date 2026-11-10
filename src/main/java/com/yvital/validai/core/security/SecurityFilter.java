package com.yvital.validai.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter { //essa validação roda a cada requisição 

    @Autowired

}

// um filtro para cada requisição, antes da requisição chegar no controller
// essa camada faz uma verificação se o usuário está autenticado, se de acordo
// com o token
// dele, ele tem permissão para fazer essa request ou não