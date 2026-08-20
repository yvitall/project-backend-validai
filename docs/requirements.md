# <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="blue" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon icon-tabler icons-tabler-outline icon-tabler-qrcode"><path stroke="none" d="M0 0h24v24H0z" fill="none" /><path d="M4 5a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M7 17l0 .01" /><path d="M14 5a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M7 7l0 .01" /><path d="M4 15a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1l0 -4" /><path d="M17 7l0 .01" /><path d="M14 14l3 0" /><path d="M20 14l0 .01" /><path d="M14 14l0 3" /><path d="M14 20l3 0" /><path d="M17 17l3 0" /><path d="M20 17l0 3" /></svg> ValidAI - System of Management and Certified Emission

# Contexto do Problema

#### **Objetivo:** Desenvolver uma API RESTful em Java (Spring Boot) para gerenciamento e emissão de certificados, focando em boas práticas, arquitetura em camadas e banco de dados relacional.

#### **Problema:** Gerenciamento manual através de planilhas e formulários básicos para inscrições e organização de alunos, além da emissão de certificados totalmente manual e sendo gerado 1/1.

#### **Solução:** Sistema de gerenciamento de eventos para instituições onde será possível cadastrar próximos eventos, gerenciar inscritos e validar presenças dos mesmos para que o certificado seja gerado automaticamente.

# Requisitos e Regras de Negócio

## 1. Perfis de Usuário
| Perfil | Descrição |
|---|---|
| Admin | Gerencia usuários, eventos e permissões gerais do sistema. |
| Organizador | Cria eventos, gerencia inscritos e valida presença |
| Participante | Visualiza eventos disponíveis, realiza inscrição e recebe certificados |

## 2. Regras de Negócio (Mínimo 10)
1. [RN01] - Um participante não pode se inscrever duas vezes no mesmo evento
2. [RN02] - Um participante só receberá seu certificado caso sua presença seja confirmada
3. [RN03] - Um participante deve obrigatóriamente ter uma conta para se cadastrar nos eventos
4. [RN04] - Um organizador só poderá criar eventos a partir da data atual 
5. [RN05] - Para um evento existir, ele deverá conter: ID, Título, Descrição, Local, Data, Hora, CH válida, Palestrante e Título/Cargo do Palestrante

## 3. Matriz de Permissões
*(A ser preenchido)*

## 4. Backlog e Critérios de Aceite
*(A ser preenchido) backlog no requirements? como é isso?*    