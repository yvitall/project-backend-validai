# 📌 Instruções Gerais (2AV) | ValidAI

## 🎯 Objetivo
Evoluir a API RESTful em Java (Spring Boot) desenvolvida na 1AV para uma versão mais completa, segura e preparada para produção, incorporando JWT, testes automatizados, boas práticas e deploy.

*Nota: O edital original menciona Python/FastAPI/Pytest, mas as exigências foram traduzidas para o ecossistema aprovado do projeto (Java/Spring Boot/JUnit).*

---

## 📅 CHECKPOINT 1 (16/11/2026) - Segurança Inicial e Autenticação
*Objetivo: Implementar a autenticação de usuários e os mecanismos iniciais de segurança.*

- [ ] Manter a aplicação da 1AV executando (CRUD de Usuários base).
- [ ] Cadastro de usuários com credenciais válidas.
- [ ] Armazenamento de senhas com hash seguro (BCrypt).
- [ ] Autenticação por login (`/auth/login`) retornando token JWT.
- [ ] Configurar chave JWT utilizando variável de ambiente (`.env`).
- [ ] Gerar token JWT com tempo de expiração definido.
- [ ] Implementar a validação do token JWT (Filtro de Segurança).
- [ ] Proteger pelo menos duas rotas da aplicação (Retornando 401 Unauthorized se sem token).
- [ ] Implementar endpoint para consultar os dados do usuário autenticado (`/usuarios/me`).
- [ ] Atualizar Swagger liberando os endpoints de autenticação.
- [ ] Código versionado no GitHub.

---

## 📅 CHECKPOINT 2 (23/11/2026) - Autorização, Matriz e Primeiros Testes
*Objetivo: Aplicar permissões aos recursos da API e iniciar a implementação dos testes.*

- [ ] Implementar pelo menos dois perfis de usuário (Ex: ADMIN e PARTICIPANT).
- [ ] Aplicar controle de acesso em pelo menos 4 endpoints (Matriz de Permissões).
- [ ] Impedir operações incompatíveis com o perfil (Retornando 403 Forbidden).
- [ ] Implementar restrição de acesso ao próprio recurso (Usuário só edita seus dados).
- [ ] Configurar ambiente de testes (JUnit 5 + Mockito).
- [ ] Desenvolver no mínimo **8 testes automatizados** (Auth, Autorização e Funcionalidades).
- [ ] Demonstrar resultado da execução dos testes.
- [ ] Matriz de permissões documentada no README.

---

## 📅 CHECKPOINT 3 (30/11/2026) - Qualidade, Regras e Preparação
*Objetivo: Consolidar a qualidade, ampliar testes e preparar para publicação.*

- [ ] Completar os CRUDs restantes do domínio (Eventos, Certificados, etc).
- [ ] Atingir pelo menos **15 testes automatizados** (cenários positivos e negativos).
- [ ] Implementar e validar as 10 Regras de Negócio previstas na 1AV.
- [ ] Realizar e documentar **3 melhorias/refatorações** técnicas no código.
- [ ] Padronizar tratamento de erros (ExceptionHandler) e status codes.
- [ ] Swagger/OpenAPI atualizado (schemas, autenticação Bearer configurada).
- [ ] Criar arquivo `.env.example` sem dados sensíveis.
- [ ] Elaborar o README.md técnico (Instalação, testes, endpoints, arquitetura).
- [ ] Preparar configurações para deploy (Conexão via variáveis de ambiente).

---

## ✅ ENTREGA FINAL E APRESENTAÇÃO (07/12/2026)
*Objetivo: Solução back-end completa, online e testada.*

- [ ] Atingir a meta de **20 testes automatizados** no total (4 Auth, 4 AuthZ, 4 CRUD, 6 RN, 2 Validação).
- [ ] Suíte de testes executando 100% sem falhas.
- [ ] Publicar a API em ambiente de produção (Ex: Render/Railway) com HTTPS.
- [ ] Banco de dados persistente em produção.
- [ ] Relatório resumido de evolução (1AV vs 2AV) no README.
- [ ] Tabela de Rastreabilidade (Requisitos/Regras vs Endpoints) no README.
- [ ] GitHub com versão final taggeada (ex: `v1.0.0`).
- [ ] Apresentação de slides abordando: Arquitetura, Segurança, Autorização, Testes, Qualidade e Deploy.
- [ ] Demonstração funcional da API em produção durante a defesa.

---

## 📊 Critérios de Avaliação (Peso 10.0)

| Critério de Avaliação | Pontuação |
| :--- | :--- |
| Evolução do MVP, funcionalidades e regras de negócio | 1,0 |
| Autenticação JWT e gerenciamento de credenciais | 1,5 |
| Autorização e controle de acesso | 1,0 |
| Segurança e configuração dos ambientes | 0,75 |
| Testes automatizados e validação dos cenários | 1,5 |
| Arquitetura, qualidade e refatoração | 1,0 |
| Documentação Swagger/OpenAPI e README | 1,0 |
| GitHub e versionamento | 0,5 |
| Deploy e funcionamento em produção | 1,0 |
| Apresentação e defesa técnica | 0,75 |
| **Total** | **10,0** |