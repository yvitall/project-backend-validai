# Arquitetura do ValidAI

## Decisão

O ValidAI adota uma arquitetura em camadas, organizada em quatro áreas: `api`, `domain`, `core` e `infra`. É a alternativa mais adequada para uma API Spring Boot de porte acadêmico: separa responsabilidades, segue convenções do framework e permite testar regras de negócio sem depender de HTTP ou PostgreSQL.

```text
src/main/java/com/yvital/validai/
├── api/
│   ├── controller/
│   ├── dto/request/
│   ├── dto/response/
│   └── mapper/
├── domain/
│   ├── model/
│   ├── enums/
│   ├── repository/
│   └── service/
├── core/
│   ├── config/
│   ├── exception/
│   └── security/                
└── infra/
    ├── database/
    ├── qrcode/
    └── storage/
```

```text
Cliente → Controller → Service → Repository → PostgreSQL
                         ↓
                   QR Code / PDF
```

## Responsabilidades

| Camada | Faz | Não faz |
|---|---|---|
| `api` | Traduz HTTP, valida DTOs, chama serviços e produz respostas. | Não contém regra de capacidade, permissão ou status. |
| `domain` | Representa entidades, enums, repositórios e regras de negócio. | Não conhece detalhes de cabeçalhos HTTP ou formato JSON. |
| `core` | Centraliza configurações, tratamento global de erros e, futuramente, JWT. | Não implementa casos de uso específicos. |
| `infra` | Encapsula detalhes técnicos de banco, token QR, arquivos PDF e storage. | Não decide se um certificado pode ser emitido. |

## Fundamentos da escolha

- **Separação de responsabilidades:** cada mudança tem lugar claro; uma alteração de JSON não exige mudar regra de inscrição.
- **Baixo acoplamento:** controller depende de serviço, e serviço depende de repositório; nenhuma camada inferior chama uma superior.
- **Testabilidade:** capacidade, duplicidade e emissão única de certificado podem ser testadas no serviço com dependências simuladas.
- **Manutenibilidade:** exceções, validações e configurações não são duplicadas entre endpoints.
- **Aderência ao Spring:** controllers, services, repositories e JPA são conceitos ensinados e amplamente usados no ecossistema.

## Decisões técnicas

| Tema | Decisão |
|---|---|
| Linguagem e framework | Java 21 e Spring Boot 4.1.0 |
| Persistência | Spring Data JPA e PostgreSQL 16. |
| Schema | Flyway; cada alteração estrutural é uma migration SQL imutável e versionada. |
| Validação | Bean Validation nos DTOs; regras que consultam banco pertencem aos serviços. |
| Erros | Handler global retorna `timestamp`, `status`, `code`, `message`, `path` e `fields` quando aplicável. |
| QR Code | Token opaco, aleatório, único por inscrição. A API valida o conteúdo lido; não implementa câmera. |
| Certificado | Registro único por inscrição; PDF em diretório local configurável, fora do Git. |
| Segurança | JWT e autorização efetiva depois da 1AV; a matriz de permissões já define o comportamento alvo. |
| Documentação | OpenAPI/Swagger para a API implementada. |