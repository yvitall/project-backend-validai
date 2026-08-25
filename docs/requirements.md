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
2. [RN02] - **User:** Para se cadastrar no sistema, é obrigatório: Nome, sobrenome, senha, email
3. [RN03] - **User:** Email: Só pode existir um usúario por email
4. [RN04] - **User:** não pode se inscrever duas vezes no mesmo evento
5. [RN05] - **Certificate:** Um participante só receberá seu certificado caso sua presença seja confirmada (validada por um organizador)
6. [RN06] - **Event** Apenas admins e organizadores poderão criar eventos.
7. [RN07] - **Event:** Para cadastrar um evento, é obrigatório: Título, Descrição, Local, Número de vagas, Data, Hora, Carga hóraria válida em horas, Palestrante e Título/Cargo do Palestrante
8. [RN08] - **Event:** Um evento só pode ser cadastrado a partir do dia seguinte, ou seja, deve ser cadastrado com pelo menos um dia de antecedência pelo organizador 
9. [RN09] - **Event:** Um evento cancelado não deve permitir novas inscrições e deve notificar usuários previamente inscritos
10. [RN10] - **Event:**  Um evento só pode ser editado pelo organizador que criou ou por um admin
11. [RN11] - **Registration:** O número de inscrições não podem exceder a quantidade de vagas de um evento
12. [RN12] - **Certificate:** A data de emissão do certificado deve ser a data de quando for emitido pelo sistema. 
13. [RN13] - **User:** Um usuário pode cancelar sua própria inscrição em um evento, desde que o evento ainda não tenha ocorrido. 

## 3. Entidades do Sistema
1. User: usuários dividido em 3 papéis (Usuário, Organizador e Admin)
2. Event: Cadastrados por organizadores ou admins
3. Registration: Relacionamento entre User e Event
5. CheckIn: Entidade que registrará presença de usuários em eventos, relacionará usuário, evento e presença
4. Certificate: se for validado (checkin), o usuário receberá seu certificado contendo informações do evento. 

**Justificativa:** Essas entidades cobrem o ciclo completo: usuários se cadastram, eventos são criados, participantes se inscrevem, organizadores validam presença e certificados são emitidos com base nas inscrições.

## 3. Matriz de Permissões
*(A ser preenchido)*

## 4. Backlog e Critérios de Aceite
*(A ser preenchido) backlog no requirements? como é isso?*    