# <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="blue" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-qrcode"><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path d="M4 5a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M7 17l0 .01" /><path d="M14 5a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M7 7l0 .01" /><path d="M4 15a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M17 7l0 .01" /><path d="M14 14l3 0" /><path d="M20 14l0 .01" /><path d="M14 14l0 3" /><path d="M14 20l3 0" /><path d="M17 17l3 0" /><path d="M20 17l0 3" /></svg> ValidAI - System of Management and Certified Emission

# Contexto do Problema

#### **Objetivo:** Desenvolver uma API RESTful em Java (Spring Boot) para gerenciamento e emissão de certificados, focando em boas práticas, arquitetura em camadas e banco de dados relacional.

#### **Problema:** Gerenciamento manual através de planilhas e formulários básicos para inscrições e organização de alunos, além da emissão de certificados totalmente manual e sendo gerado aluno por aluno.

#### **Solução:** Sistema de gerenciamento de eventos para instituições onde será possível cadastrar próximos eventos, gerenciar inscritos e validar presenças dos mesmos para que o certificado seja gerado automaticamente.
- Cadastro de usuários (participantes, organizadores, administradores).
- Criação e gerenciamento de eventos.
- Inscrição de participantes em eventos.
- Emissão de certificados para participantes inscritos.

# Requisitos e Regras de Negócio

## 1. Perfis de Usuário
| Perfil | Descrição |
|---|---|
| Admin | Gerencia usuários, eventos e permissões gerais do sistema. |
| Organizador | Cria eventos, gerencia inscritos e valida presença |
| Participante | Visualiza eventos disponíveis, realiza inscrição e recebe certificados |

## 2. Regras de Negócio (Mínimo 10)
1. [RN01] - **User:** Para se inscrever em um evento, é necessário ser cadastrado no sistema
2. [RN02] - **User:** Para se cadastrar no sistema, é obrigatório: Nome, sobrenome, email, senha.
3. [RN03] - **User:** Email: Só pode existir um usúario por email
4. [RN04] - **User:** não pode se inscrever duas vezes no mesmo evento
13. [RN13] - **User:** Um usuário pode cancelar sua própria inscrição em um evento, desde que o evento ainda não tenha ocorrido. 
6. [RN06] - **Event** Apenas admins e organizadores poderão criar eventos.
7. [RN07] - **Event:** Para cadastrar um evento, é obrigatório: Título, Descrição, Local, Número de vagas, Data, Hora, Carga hóraria válida em horas, Palestrante e Título/Cargo do Palestrante
8. [RN08] - **Event:** Um evento só pode ser cadastrado a partir do dia seguinte, ou seja, deve ser cadastrado com pelo menos um dia de antecedência pelo organizador 
9. [RN09] - **Event:** Um evento cancelado não deve permitir novas inscrições e deve notificar usuários previamente inscritos
10. [RN10] - **Event:**  Um evento só pode ser editado pelo organizador que criou ou por um admin
11. [RN11] - **Registration:** O número de inscrições não podem exceder a quantidade de vagas de um evento
12. [RN12] - **Certificate:** A data de emissão do certificado deve ser a data de quando for emitido pelo sistema. 
5. [RN05] - **Certificate:** Um participante só receberá seu certificado caso sua presença seja confirmada (validada por um organizador)

## 3. Entidades do Sistema
* User: usuários dividido em 3 papéis (Usuário, Organizador e Admin)
    * id, firstName, lastName, email, passwordHash, role, createdAt
* Event: Cadastrados por organizadores ou admins
    * id, title, description, location, date, hour, workload (INTEGER), capacity, speakerName, speakerTitle, status(ACTIVE/CANCELED/FINISHED), organizerId
* Registration: Relacionamento entre User e Event
    * id, userId, eventId, registrationDate, status (ACTIVE/CANCELED/ATTENDED), attendanceConfirmed (boolean), attendanceConfirmedAt (timestamp), qrCode
* Certificate: se for validado (checkin), o usuário receberá seu certificado contendo informações do evento. 
    * id, registrationId, issueDate, certificateCode, pdfUrl

**Justificativa:** Essas entidades cobrem o ciclo completo: usuários se cadastram, eventos são criados, participantes se inscrevem, organizadores validam presença e certificados são emitidos com base nas inscrições.

## Requisitos Funcionais
1. [RF01] Um usuário deve conseguir se cadastrar
2. [RF02] Um organizador deve conseguir cadastrar um evento
3. [RF03] O sistema deve emitir um certificado de participação a cada evento em que for validado via QRCODE a presença do usuário
4. [RF04]

## Modelagem Conceitual
![ModConceitual](assets/modelagemConceitual.png)

## Modelagem Lógica - DER
![ModLogica](assets/modelagemLogica.png)

## Dicionário de Dados
| Entidade | Campo | Tipo | Obrigatório | Regra |
| -------- | ----- | ---- | --- | ----- |
| USER | id | BIGINT | SIM | PK; UNIQUE |
| USER | firstName | VARCHAR(50) | SIM | Mínimo 3 caracteres |
| USER | lastName | VARCHAR(50) | SIM | Mínimo 3 caracteres |
| USER | email | VARCHAR(255) | SIM | UNIQUE; Formato Email Válido |
| USER | passwordHash | VARCHAR(255) | SIM | Armazenar Hash (BCrypt) |
| USER | role | VARCHAR(12) | SIM | Cargo do usuário (Participante, Organizador, Admin) |
| USER | createdAt | TIMESTAMP | SIM | Log de registro |
| REGISTRATION | id | BIGINT | SIM | PK; UNIQUE |
| REGISTRATION | (idUser, idEvent) | COMPOSITE UNIQUE | SIM | Garante que um usuário não se inscreva duas vezes no mesmo evento |
| REGISTRATION | registrationAt | TIMESTAMP | SIM | Data e hora da inscrição no evento |
| REGISTRATION | status | VARCHAR(8) | SIM | Valores: (ACTIVE/CANCELED/ATTENDED) |
| REGISTRATION | attendanceConfirmed | BOOLEAN | SIM | registrar presença |
| REGISTRATION | attendanceConfirmedAt | TIMESTAMP | NÃO | registrar data e hora da presença |
| REGISTRATION | qrCode | VARCHAR(255) | SIM | UNIQUE; hash do qrcode de validação de presença do participante |
| EVENT | id | BIGINT | SIM | PK; UNIQUE |
| EVENT | idOrganizer | BIGINT | SIM | FK |
| EVENT | title | VARCHAR(255) | SIM | Mínimo 5 caracteres |
| EVENT | description | VARCHAR(255) | SIM | Mínimo 10 caracteres |
| EVENT | location | VARCHAR(255) | SIM | Mínimo 10 caracteres | 
| EVENT | date | DATE | SIM | A partir da data seguinte |
| EVENT | hour | TIME | SIM | Horário do evento |
| EVENT | workload | INTEGER | SIM | Valor inteiro que representa CH válida
| EVENT | capacity | INTEGER | SIM | Valor inteiro que representa quantidade de participantes permitidos
| EVENT | speakerName | VARCHAR(50) | SIM | Mínimo 3 caracteres |
| EVENT | speakerTitle | VARCHAR(50) | SIM | Mínimo 3 caracteres |
| EVENT | status | VARCHAR(8) | SIM | Valores: (ACTIVE/CANCELED/FINISHED)
| CERTIFICATE | id | BIGINT | SIM | PK; UNIQUE |
| CERTIFICATE | idRegistration | BIGINT | SIM | FK; UNIQUE |
| CERTIFICATE | issueDate | DATE | SIM | Data atual da emissão do certificado |
| CERTIFICATE | certificateCode | VARCHAR(255) | SIM | UNIQUE; Hash código do certificado |
| CERTIFICATE | pdfUrl | VARCHAR(255) | SIM | UNIQUE; Link de URL do certificado |

## Contratos da API
## 1.1 Cadastro de Usuário
**POST /api/v1/users/register**
- Descrição: Cria um novo usuário com papel padrão PARTICIPANT. Apenas administradores poderão alterar papéis posteriormente.
**Entrada (JSON)**
```json
{
    "firstName": "Jucelio",
    "lastName": "Testador",
    "email": "juceliotestador@email.com",
    "password": "Abcd!123",
}
```
**Saída (JSON): `201 Created`**
```json
{
    "id": 1,
    "firstName": "Jucelio",
    "lastName": "Testador",
    "email": "juceliotestador@email.com",
    "role": "PARTICIPANT",
    "createdAt": "2026-09-01T10:00:00"
}
```
| Código | Significado |
| --- | --- |
| 201 | Usuário criado com sucesso. |
| 400 | Dados inválidos (ex: email mal formatado, senha fraca). |
| 422 | Email já cadastrado (violação de unicidade). |

## 1.2 Login (Autenticação)
**POST /api/v1/auth/login**
- Descrição: Autentica o usuário e retorna um token JWT para acesso aos demais endpoints.
    - Autenticação básica de credenciais de usuário, autenticação via JWT (será implementado na segunda unidade).

**Entrada (JSON)**
```json
{
  "email": "joao@email.com",
  "password": "Senha@123"
}
```
**Saída (JSON): `200 OK`**
```json
{
  //"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  //"type": "Bearer",
  "id": 1,
  "email": "joao@email.com",
  "role": "PARTICIPANT"
}
```
| Código | Significado |
| --- | --- |
| 200 | Login bem-sucedido |
| 401 | Credenciais inválidas |
| 400 | Campos obrigátorios ausentes |

## 1.3 Listagem de Eventos
**GET /api/v1/events**
- Descrição: Retorna todos os eventos, com opção de filtros por status.
    - Parâmetros(opcionais):
    * status - ACTIVE, CANCELED, FINISHED

**Saída (JSON): `200 OK`**
```json
[
  {
    "id": 1,
    "title": "QA na prática",
    "description": "Aprenda o que é e o que faz um QA na prática",
    "location": "Teatro Algibeira",
    "date": "2026-10-10",
    "hour": "08:30",
    "workload": 20,
    "capacity": 50,
    "speakerName": "Victor Oliveira",
    "speakerTitle": "Quality Assurance Specialist",
    "status": "ACTIVE",
    "organizerId": 2
  }
]
```

| Código | Significado |
| --- | --- |
| 200 | Lista retornada (pode ser vazia) |
| 400 | Parâmetros inválidos |

## 1.4 Criação de Evento
``POST /api/v1/events/?``
- Descrição: Cria um novo evento. Apenas usuários com papel ``ORGANIZER`` ou ``ADMIN`` podem acessar.

**Entrada (JSON)**
```json 
{
  "title": "Aprenda GIT de uma vez por todas!",
  "description": "Workshop com Hands-on para prática ativa de versionamento com GIT",
  "location": "Teatro Algibeira",
  "date": "2026-10-16",
  "hour": "14:30",
  "workload": 10,
  "capacity": 50,
  "speakerName": "Victor Oliveira",
  "speakerTitle": "Quality Assurance Specialist"
}
```
**Saída (JSON): `201 (CREATED)`**
```json
{
    "id": 6,
    "title": "Aprenda GIT de uma vez por todas!",
    "description": "Workshop com Hands-on para prática ativa de versionamento com GIT",
    "location": "Teatro Algibeira",
    "date": "2026-10-16",
    "hour": "14:30",
    "workload": 10,
    "capacity": 50,
    "speakerName": "Victor Oliveira",
    "speakerTitle": "Quality Assurance Specialist",
    "status": "ACTIVE",
    "organizerId": 2
}
```
| Código | Significado |
| --- | --- |
| 201 | Evento criado com sucesso |
| 400 | Dados inválidos (ex: data passada, capacidade e workload <0) |
| 401 | Usuário não autenticado |
| 403 | Usuário não tem permissão (não é organizador/admin)
| 422 | Violação da regra de negócio |

## 1.5 Inscrição em Evento
``POST /api/v1/events/{eventId}/registration``
- Descrição: Inscrição de um usuário autenticado em um evento específico. O ``eventId`` é passado na URL

**Entrada: N/A**
**Saída (JSON): ``201 CREATED``**
```json
    {
        "id": 10,
        "userId": 1,
        "eventId": 1,
        "registrationAt": "2026-09-01T10:30:00",
        "status": "ACTIVE",
        "attendanceConfirmed": false,
        "attendanceConfirmedAt": null,
        "qrCode": "abc123def456..."
    }
```
| Código | Significado |
| --- | --- |
| 201 | Inscrição realizada com sucesso |
| 400 | Regra violada (ex: já inscrito, evento lotado ou cancelado) |
| 401 | Usuário não autenticado |
| 404 | Evento não encontrado |
| 409 | Usuário já inscrito no evento |

## 1.6 Validação de Presença (via QR Code)
``PATCH /api/v1/registration/{registrationId}/confirm-attendance``
- Descrição: Organizador ou admin confirma a presença de um participante, atualizando o status da inscrição para `ATTENDED` e registrando a data/hora.
**Entrada: N/A**
**Saída (JSON): ``200 OK``**
```json
    {
        "id": 10,
        "userId": 1,
        "eventId": 1,
        "status": "ATTENDED",
        "attendanceConfirmed": true,
        "attendanceConfirmedAt": "2026-09-10T15:30:00"
    }
```
| Código | Significado |
| --- | --- |
| 200 | Presença confirmada (OK) |
| 400 | Evento ainda não ocorreu ou presença já está confirmada |
| 401 | Usuário não autenticado |
| 403 | Usuário não é organizador/admin do evento |
| 404 | Inscrição não encontrada |

## 1.7 Emissão de Certificado


## Matriz de Permissões
*(A ser preenchido)*

## Backlog e Critérios de Aceite
*(A ser preenchido) backlog no requirements? como é isso?*    