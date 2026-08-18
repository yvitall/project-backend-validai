# Projeto da Disciplina — 1ª Avaliação (1AV)

## 📅 Datas importantes

| Marco | Data |
|---|---|
| Envio dos nomes dos participantes (formulário) | até **31/08/2026** |
| Checkpoint 1 (individual, vale 1 ponto) | **14/09/2026** |
| Checkpoint 2 (individual, vale 1 ponto) | **21/09/2026** |
| Checkpoint 3 (individual, vale 1 ponto) | **28/09/2026** |
| Apresentação final dos projetos (100%) | **05/10/2026** |

## 👥 Formato do grupo

- Individual **ou** em grupo de até 4 pessoas.
- Durante o período de desenvolvimento, as aulas serão dedicadas ao acompanhamento dos projetos + aulas teóricas complementares.

## 🎯 Tema (implícito no material)

O material de referência usa como exemplo um sistema de **gestão de eventos acadêmicos com emissão de certificados** — inscrições, controle de participantes e emissão de certificado para quem participou. Esse é o eixo temático a seguir:

- API para gerenciar **eventos**, **inscrições**, **usuários** e **emissão de certificados**, substituindo controles manuais por uma solução centralizada.
- Perguntas a responder antes de começar:
  - Qual problema o sistema resolve?
  - Quem vai usar?
  - Qual o objetivo da API?
  - Qual o escopo da primeira versão?

### 💡 Ideias levantadas para o projeto

- Cadastro de eventos por parte da Faculdade/Coordenação/GEIA.
- Inscrição de alunos nos eventos via app/sistema.
- Validação de presença via leitura de QR Code.
- Possíveis extras a avaliar depois: IA (ex: DeepSeek) e/ou OCR aplicados a algum ponto do fluxo.

**Fluxo (user story) definido:**

1. Faculdade/Coordenador/GEIA cadastra um evento no sistema e pode ver a lista de usuários inscritos nesse evento.
2. Aluno/Usuário vê a lista de eventos disponíveis (título e descrição) e se inscreve no evento desejado.
3. Durante o evento, um organizador designado valida a presença do aluno/usuário via leitura de QR Code.

## 🖥️ Stack tecnológica e configurações

- **Linguagem:** Java 21 (LTS)
- **Framework:** Spring Boot 4.1.0
- **Gerenciador de dependências:** Gradle
- **Banco de dados:** PostgreSQL
- **Arquitetura:** em camadas (Controller, Service, Repository, Model)
- **Versionamento:** Git — repositório público, com fluxo de desenvolvimento em branches

## 📦 Dependências do Projeto
- **Spring Web:** Para criação dos endpoints RESTful.
- **Spring Data JPA:** Para persistência de dados e integração com o PostgreSQL.
- **Spring Validation:** Para validação dos dados de entrada (ex: regras do ISBN).
- **Spring Boot DevTools:** Para agilizar o desenvolvimento (Live Reload).
- **Lombok:** Para redução de código boilerplate (Getters, Setters, Construtores).
- **PostgreSQL Driver:** Driver de conexão com o banco de dados.

## 🏆 Critérios de avaliação (Primeira Unidade)

| Componente | Peso |
|---|---|
| Projeto da disciplina | até 6 pontos |
| — Apresentação final | até 3 pontos |
| — Cada checkpoint individual (x3) | até 1 ponto cada |
| Exercícios | até 4 pontos |

### Distribuição de pontos da Primeira Entrega (10,0 no total)

| Critério | Pontos |
|---|---|
| Requisitos funcionais e não funcionais | 1,0 |
| Regras de negócio | 1,0 |
| DER + dicionário de dados | 1,5 |
| Rotas + contratos + status codes | 1,5 |
| Arquitetura e estrutura do projeto | 1,0 |
| Matriz de permissões | 1,0 |
| Backlog + critérios de aceitação | 1,0 |
| Swagger/API inicial funcionando | 1,0 |
| Organização no GitHub | 1,0 |
| Apresentação/defesa técnica | 1,0 |

## ⚠️ O que a Entrega 1 exige de fato

- **Não precisa ser o sistema completo funcionando.** O peso maior está na documentação (contexto, regras de negócio, DER, dicionário, contratos, status codes, matriz de permissões, backlog, critérios de aceite).
- O código exigido é um **MVP mínimo**: projeto rodando, Swagger acessível, conexão com banco funcionando, **uma única entidade** com CRUD básico, models/schemas dessa entidade, rotas organizadas e tudo versionado no GitHub.
- **Fica para a 2ª unidade:** autenticação/JWT, separação em `services`/`repositories`, testes automatizados.
- O **checklist** abaixo (16 itens) é a versão detalhada de como construir cada entrega; a **tabela de obrigatoriedade** mais abaixo é a versão resumida em lista, para conferência final antes de entregar — não são exigências separadas, é o mesmo conteúdo em dois formatos.

## ✅ Checklist obrigatório — Primeira Entrega (Concepção Técnica + MVP Inicial)

1. **Contexto do problema** — problema, público, objetivo, escopo da v1.
2. **Perfis de usuário** — ex.: Admin, Organizador, Participante (base para autorização na 2ª unidade).
3. **Regras de negócio** — mínimo de **10**. Sem elas o projeto vira CRUD simples.
4. **Entidades principais** — ex.: Usuário, Evento, Inscrição, Certificado, Categoria/Atividade — com justificativa de cada uma.
5. **Modelo de dados / DER** (obrigatório) — tabelas, campos, PKs, FKs, relacionamentos, cardinalidade.
6. **Dicionário de dados** — entidade, campo, tipo, obrigatoriedade, regra.
7. **Contratos de entrada e saída da API** — corpo de request/response de cada rota (não só listar as rotas).
8. **Status codes** usados (201, 200, 204, 404, 400, 401, 403, 422 etc.).
9. **Padrão de resposta e erro** — formato padronizado de sucesso/erro, com justificativa.
10. **Matriz de permissões** — funcionalidade x perfil (mesmo que a autorização só seja implementada na 2ª unidade).
11. **Estrutura inicial do projeto** — organização de pastas (mínimo: api/core/models/schemas/main; services/repositories/tests podem vir depois).
12. **Tecnologias escolhidas e justificativa** — papel de cada uma no projeto.
13. **Estratégia de banco e migrations** — banco escolhido, tabelas iniciais, relacionamentos, migration inicial (se possível, já criada).
14. **Backlog do projeto** — mínimo de **10** user stories, com prioridade.
15. **Critérios de aceitação** — mínimo de **10**, por funcionalidade principal.
16. **Protótipo inicial da API (MVP técnico)**:
    - projeto criado e rodando;
    - Swagger (ou equivalente) abrindo;
    - conexão inicial com o banco;
    - pelo menos uma entidade com CRUD básico;
    - schemas de validação;
    - models do banco;
    - rotas organizadas;
    - repositório no GitHub.

## 📋 Tabela de obrigatoriedade — Entrega 1

Todos os itens abaixo são **obrigatórios**:

- Contexto do problema
- Requisitos funcionais e não funcionais
- Perfis de usuário
- Regras de negócio
- DER / modelo de dados
- Dicionário de dados
- Rotas da API
- Contratos JSON
- Status codes
- Matriz de permissões
- Arquitetura do projeto
- Stack e justificativa
- Backlog inicial
- Critérios de aceitação
- Repositório GitHub
- Swagger inicial funcionando
- CRUD de pelo menos uma entidade