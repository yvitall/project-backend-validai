# Contratos da API - ValidAI

Este documento define todos os endpoints da API REST do sistema **ValidAI**, incluindo métodos HTTP, caminhos, exemplos de entrada e saída (JSON) e status codes possíveis. Os contratos estão alinhados ao dicionário de dados e às regras de negócio.

Todos os endpoints seguem o prefixo `/api/v1/`. A autenticação será via JWT, exceto `/users/register` e `/auth/login`; JWT será implementado na próxima unidade.

---

## 1.1 Cadastro de Usuário
`POST /api/v1/users/register`

- **Descrição:** Cria novo usuário com papel padrão `PARTICIPANT`. Apenas administradores poderão alterar papéis posteriormente.

**Entrada (JSON)**
```json
{
  "firstName": "Jucelio",
  "lastName": "Testador",
  "email": "juceliotestador@email.com",
  "password": "Abcd!123"
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
|--------|-------------|
| 201 | Usuário criado com sucesso. |
| 400 | Dados inválidos, como e-mail malformado ou senha fraca. |
| 409 | E-mail já cadastrado (violação de unicidade). |

---

## 1.2 Login (Autenticação)
`POST /api/v1/auth/login`

- **Descrição:** Autentica o usuário e, na fase de segurança, retorna token JWT para acesso aos demais endpoints.

**Entrada (JSON)**
```json
{
  "email": "juceliotestador@email.com",
  "password": "Abcd!123"
}
```

**Saída (JSON): `200 OK` — contrato da fase JWT**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "id": 1,
  "email": "juceliotestador@email.com",
  "role": "PARTICIPANT"
}
```

| Código | Significado |
|--------|-------------|
| 200 | Login bem-sucedido. |
| 400 | Campos obrigatórios ausentes ou inválidos. |
| 401 | Credenciais inválidas. |

---

## 1.3 Listagem de Eventos
`GET /api/v1/events`

- **Descrição:** Retorna todos os eventos cadastrados. Aceita parâmetros opcionais de filtro via query string.
- **Parâmetro opcional:** `status=ACTIVE`, `CANCELED` ou `FINISHED`.

**Entrada:** não possui corpo.

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
|--------|-------------|
| 200 | Lista retornada com sucesso; pode ser vazia. |
| 400 | Parâmetro de filtro inválido. |

---

## 1.4 Criação de Evento
`POST /api/v1/events`

- **Descrição:** Cria novo evento. Apenas usuários com papel `ORGANIZER` ou `ADMIN` podem acessar. O `organizerId` é extraído do usuário logado.

**Entrada (JSON)**
```json
{
  "title": "Aprenda GIT de uma vez por todas!",
  "description": "Workshop com hands-on para prática ativa de versionamento com GIT",
  "location": "Teatro Algibeira",
  "date": "2026-10-16",
  "hour": "14:30",
  "workload": 10,
  "capacity": 50,
  "speakerName": "Victor Oliveira",
  "speakerTitle": "Quality Assurance Specialist"
}
```

**Saída (JSON): `201 Created`**
```json
{
  "id": 6,
  "title": "Aprenda GIT de uma vez por todas!",
  "description": "Workshop com hands-on para prática ativa de versionamento com GIT",
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
|--------|-------------|
| 201 | Evento criado com sucesso. |
| 400 | Dados inválidos, como capacidade não positiva ou campo vazio. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não tem permissão. |
| 422 | Violação de regra de negócio, como data anterior ao dia seguinte. |

---

## 1.5 Inscrição em Evento
`POST /api/v1/events/{eventId}/registrations`

- **Descrição:** Inscreve o usuário autenticado em evento específico. O `eventId` é passado na URL.
- **Autenticação:** requer token JWT.

**Entrada:** nenhum corpo; o usuário é identificado pelo token JWT.

**Saída (JSON): `201 Created`**
```json
{
  "id": 10,
  "userId": 1,
  "eventId": 1,
  "registrationAt": "2026-09-01T10:30:00",
  "status": "ACTIVE",
  "attendanceConfirmed": false,
  "attendanceConfirmedAt": null,
  "qrCode": "uFPyRk87C5BS1UqXgBxK0mG6..."
}
```

| Código | Significado |
|--------|-------------|
| 201 | Inscrição realizada e token QR gerado com sucesso. |
| 401 | Usuário não autenticado. |
| 404 | Evento não encontrado. |
| 409 | Usuário já inscrito no evento. |
| 422 | Evento lotado, cancelado ou regra de inscrição violada. |

---

## 1.6 Validação de Presença (via QR Code)
`PATCH /api/v1/events/{eventId}/registrations/confirm-attendance`

- **Descrição:** Organizador proprietário do evento ou admin confirma presença a partir do conteúdo lido no QR Code. A inscrição passa para `ATTENDED` e registra data/hora.
- **Autenticação:** requer token JWT.

**Entrada (JSON)**
```json
{
  "qrCode": "uFPyRk87C5BS1UqXgBxK0mG6..."
}
```

**Saída (JSON): `200 OK`**
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
|--------|-------------|
| 200 | Presença confirmada com sucesso. |
| 400 | Corpo da requisição inválido. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é organizador do evento nem admin. |
| 404 | Evento, QR Code ou inscrição não encontrado. |
| 409 | Presença já confirmada. |
| 422 | Evento ainda não ocorreu ou inscrição não está ativa. |

---

## 1.7 Emissão de Certificado
`POST /api/v1/certificates`

- **Descrição:** Gera certificado para inscrição que já teve presença confirmada. O sistema cria registro com código único e URL/caminho para download do PDF.
- **Autenticação:** requer token JWT; permitido ao titular, organizador do evento ou admin.

**Entrada (JSON)**
```json
{
  "registrationId": 10
}
```

**Saída (JSON): `201 Created`**
```json
{
  "id": 5,
  "registrationId": 10,
  "issueDate": "2026-09-10",
  "certificateCode": "CERT-ABC123",
  "pdfUrl": "/api/v1/certificates/5/download"
}
```

| Código | Significado |
|--------|-------------|
| 201 | Certificado gerado com sucesso. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não tem permissão sobre a inscrição. |
| 404 | Inscrição não encontrada. |
| 409 | Certificado já emitido para a inscrição. |
| 422 | Inscrição sem presença confirmada. |

---

## 1.8 Visualização/Download do Certificado
`GET /api/v1/certificates/{certificateId}/download`

- **Descrição:** Retorna o arquivo PDF do certificado. Apenas o titular, organizador do evento ou admin podem acessar.
- **Autenticação:** requer token JWT.

**Entrada:** nenhum corpo.

**Saída:** `200 OK` com o arquivo `application/pdf`. Não há corpo JSON na resposta.

| Código | Significado |
|--------|-------------|
| 200 | Arquivo enviado com sucesso. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não tem permissão. |
| 404 | Certificado ou arquivo não encontrado. |

---

## 1.9 Listagem de Usuários (Admin)
`GET /api/v1/users`

- **Descrição:** Lista todos os usuários cadastrados. Apenas administradores podem acessar.
- **Autenticação:** requer token JWT.
- **Parâmetro opcional:** `?role=ORGANIZER`, `ADMIN` ou `PARTICIPANT`.

**Entrada:** nenhum corpo.

**Saída (JSON): `200 OK`**
```json
[
  {
    "id": 1,
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@email.com",
    "role": "PARTICIPANT",
    "createdAt": "2026-09-01T10:00:00"
  },
  {
    "id": 2,
    "firstName": "Maria",
    "lastName": "Souza",
    "email": "maria@email.com",
    "role": "ORGANIZER",
    "createdAt": "2026-09-01T11:00:00"
  }
]
```

| Código | Significado |
|--------|-------------|
| 200 | Lista retornada com sucesso. |
| 400 | Papel usado no filtro é inválido. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é administrador. |

---

## 1.10 Atualização de Papel (Admin)
`PATCH /api/v1/users/{userId}/role`

- **Descrição:** Altera o papel de usuário. Apenas administradores podem acessar.
- **Autenticação:** requer token JWT.

**Entrada (JSON)**
```json
{
  "role": "ORGANIZER"
}
```

**Saída (JSON): `200 OK`**
```json
{
  "id": 1,
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao@email.com",
  "role": "ORGANIZER",
  "createdAt": "2026-09-01T10:00:00"
}
```

| Código | Significado |
|--------|-------------|
| 200 | Papel atualizado com sucesso. |
| 400 | Papel inválido. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é administrador. |
| 404 | Usuário não encontrado. |

---

## 1.11 Edição de Evento
`PATCH /api/v1/events/{eventId}`

- **Descrição:** Atualiza parcialmente um evento ativo. Apenas organizador proprietário ou admin podem acessar; `organizerId` não pode ser alterado.
- **Autenticação:** requer token JWT.

**Entrada (JSON) — exemplo**
```json
{
  "capacity": 70,
  "location": "Auditório Principal"
}
```

**Saída (JSON): `200 OK`**
```json
{
  "id": 6,
  "title": "Aprenda GIT de uma vez por todas!",
  "location": "Auditório Principal",
  "capacity": 70,
  "status": "ACTIVE",
  "organizerId": 2
}
```

| Código | Significado |
|--------|-------------|
| 200 | Evento atualizado com sucesso. |
| 400 | Campo enviado é inválido. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é proprietário nem admin. |
| 404 | Evento não encontrado. |
| 409 | Evento cancelado/finalizado não pode ser editado. |
| 422 | Regra de negócio, como data inválida, foi violada. |

---

## 1.12 Cancelamento de Evento
`PATCH /api/v1/events/{eventId}/cancel`

- **Descrição:** Cancela evento ativo. Apenas organizador proprietário ou admin podem acessar. Eventos cancelados não recebem novas inscrições.
- **Autenticação:** requer token JWT.

**Entrada:** nenhum corpo.

**Saída (JSON): `200 OK`**
```json
{
  "id": 6,
  "title": "Aprenda GIT de uma vez por todas!",
  "status": "CANCELED",
  "organizerId": 2
}
```

| Código | Significado |
|--------|-------------|
| 200 | Evento cancelado com sucesso. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é proprietário nem admin. |
| 404 | Evento não encontrado. |
| 409 | Evento já cancelado ou finalizado. |

---

## 1.13 Cancelamento de Inscrição
`PATCH /api/v1/registrations/{registrationId}/cancel`

- **Descrição:** Cancela inscrição ativa antes da ocorrência do evento. Somente o titular da inscrição pode acessar.
- **Autenticação:** requer token JWT.

**Entrada:** nenhum corpo.

**Saída (JSON): `200 OK`**
```json
{
  "id": 10,
  "userId": 1,
  "eventId": 1,
  "status": "CANCELED",
  "attendanceConfirmed": false,
  "attendanceConfirmedAt": null
}
```

| Código | Significado |
|--------|-------------|
| 200 | Inscrição cancelada com sucesso. |
| 401 | Usuário não autenticado. |
| 403 | Inscrição não pertence ao usuário autenticado. |
| 404 | Inscrição não encontrada. |
| 409 | Inscrição já está cancelada ou atendida. |
| 422 | Evento já ocorreu. |

---

## 1.14 Minhas Inscrições
`GET /api/v1/users/me/registrations`

- **Descrição:** Lista inscrições do usuário autenticado, com um resumo do evento associado.
- **Autenticação:** requer token JWT.

**Entrada:** nenhum corpo.

**Saída (JSON): `200 OK`**
```json
[
  {
    "id": 10,
    "eventId": 1,
    "status": "ACTIVE",
    "registrationAt": "2026-09-01T10:30:00",
    "event": {
      "title": "QA na prática",
      "date": "2026-10-10",
      "hour": "08:30",
      "status": "ACTIVE"
    }
  }
]
```

| Código | Significado |
|--------|-------------|
| 200 | Inscrições retornadas com sucesso; a lista pode ser vazia. |
| 401 | Usuário não autenticado. |
