# Contratos da API - ValidAI

Este documento define todos os endpoints da API REST do sistema **ValidAI**, incluindo métodos HTTP, caminhos, exemplos de entrada e saída (JSON), e status codes possíveis. Os contratos estão alinhados com o dicionário de dados e as regras de negócio estabelecidas.

---

## 1.1 Cadastro de Usuário
`POST /api/v1/users/register`

- **Descrição:** Cria um novo usuário com papel padrão `PARTICIPANT`. Apenas administradores poderão alterar papéis posteriormente.

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
| 400 | Dados inválidos (ex: email mal formatado, senha fraca). |
| 422 | Email já cadastrado (violação de unicidade). |

---

## 1.2 Login (Autenticação)
`POST /api/v1/auth/login`

- **Descrição:** Autentica o usuário e retorna um token JWT para acesso aos demais endpoints.
    - Autenticação básica de credenciais de usuário,. Autenticação via JWT (será implementado na segunda unidade).

**Entrada (JSON)**
```json
{
    "email": "juceliotestador@email.com",
    "password": "Abcd!123"
}
```

**Saída (JSON): `200 OK`**
```json
{
    "id": 1,
    "email": "juceliotestador@email.com",
    "role": "PARTICIPANT"
}
```

| Código | Significado |
|--------|-------------|
| 200 | Login bem-sucedido. |
| 401 | Credenciais inválidas (email ou senha incorretos). |
| 400 | Campos obrigatórios ausentes. |

---

## 1.3 Listagem de Eventos
`GET /api/v1/events`

- **Descrição:** Retorna todos os eventos cadastrados. Aceita parâmetros opcionais de filtro via query string.
    - Parâmetros(opcionais):
    * status= - `ACTIVE`, `CANCELED`, `FINISHED`

**Saída (JSON): `200 OK`**
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
| 200 | Lista retornada com sucesso (pode ser vazia). |
| 400 | Parâmetros de filtro inválidos. |

---

## 1.4 Criação de Evento
`POST /api/v1/events`

- **Descrição:** Cria um novo evento. Apenas usuários com papel `ORGANIZER` ou `ADMIN` podem acessar. O campo `organizerId` é extraído do usuário logado.

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
|--------|-------------|
| 201 | Evento criado com sucesso. |
| 400 | Dados inválidos (ex: capacidade <= 0, campos vazios). |
| 401 | Usuário não autenticado. |
| 403 | Usuário não tem permissão (não é organizador/admin). |
| 422 | Violação de regra de negócio (ex: data anterior ao dia seguinte). |

---

## 1.5 Inscrição em Evento
`POST /api/v1/events/{eventId}/registrations`

- **Descrição:** Inscreve o usuário autenticado em um evento específico. O `eventId` é passado na URL.
- **Autenticação:** Requer token JWT.

**Entrada:** Nenhum corpo (o usuário é identificado pelo token JWT).

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
    "qrCode": "abc123def456..."
}
```

| Código | Significado |
|--------|-------------|
| 201 | Inscrição realizada com sucesso. |
| 400 | Regra violada (ex: evento lotado, evento cancelado). |
| 401 | Usuário não autenticado. |
| 404 | Evento não encontrado. |
| 409 | Usuário já inscrito neste evento (violação de unicidade composta). |

---

## 1.6 Validação de Presença (via QR Code)
`PATCH /api/v1/registrations/{registrationId}/confirm-attendance`

- **Descrição:** Organizador ou admin confirma a presença de um participante, atualizando o status da inscrição para `ATTENDED` e registrando a data/hora. O `registrationId` é passado na URL.

**Entrada: N/A**.

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
| 400 | Evento ainda não ocorreu ou presença já foi confirmada anteriormente. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é organizador/admin do evento. |
| 404 | Inscrição não encontrada. |

---

## 1.7 Emissão de Certificado
`POST /api/v1/certificates`

- **Descrição:** Gera o certificado para uma inscrição que já teve presença confirmada. O sistema cria o registro com código único e URL para download do PDF.
- **Autenticação:** Requer token JWT.

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
    "pdfUrl": "https://storage.validai.com/certificates/5.pdf"
}
```

| Código | Significado |
|--------|-------------|
| 201 | Certificado gerado com sucesso. |
| 400 | Inscrição sem presença confirmada ou evento ainda não ocorreu. |
| 404 | Inscrição não encontrada. |
| 409 | Certificado já foi emitido para esta inscrição. |

---

## 1.8 Visualização/Download do Certificado
`GET /api/v1/certificates/{certificateId}/download`

- **Descrição:** Retorna o arquivo PDF do certificado (ou redireciona para a URL de armazenamento). O `certificateId` é passado na URL.
- **Autenticação:** Requer token JWT.

**Entrada:** Nenhum corpo.

**Saída:** O arquivo PDF (ou redirecionamento HTTP 302 para a URL armazenada). Não há corpo JSON na resposta.

| Código | Significado |
|--------|-------------|
| 200 | Arquivo enviado com sucesso. |
| 302 | Redirecionamento para a URL do PDF no storage. |
| 401 | Usuário não autenticado. |
| 403 | Usuário não tem permissão (apenas o dono, organizador ou admin). |
| 404 | Certificado não encontrado. |

---

## 1.9 Listagem de Usuários (Admin)
`GET /api/v1/users`

- **Descrição:** Lista todos os usuários cadastrados, com informações de papel (`role`). Apenas administradores podem acessar.
- **Autenticação:** Requer token JWT.

**Parâmetro (opcional):**
- `?role=ORGANIZER` (filtra por papel: `ADMIN`, `ORGANIZER` ou `PARTICIPANT`)

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
| 401 | Usuário não autenticado. |
| 403 | Usuário não é administrador. |

---

## 1.10 Atualização de Papel (Admin)
`PATCH /api/v1/users/{userId}/role`

- **Descrição:** Altera o papel (`role`) de um usuário. Apenas administradores podem acessar. O `userId` é passado na URL.
- **Autenticação:** Requer token JWT.

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
    "updatedAt": "2026-09-01T12:00:00"
}
```

| Código | Significado |
|--------|-------------|
| 200 | Papel atualizado com sucesso. |
| 400 | Papel inválido (ex: valor diferente de ADMIN/ORGANIZER/PARTICIPANT). |
| 401 | Usuário não autenticado. |
| 403 | Usuário não é administrador. |
| 404 | Usuário não encontrado. |
