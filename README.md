 ---

# SecureWallet API

## Sobre o Projeto

A **SecureWallet API** é o motor backend de uma carteira digital (e-wallet) construída com rigorosos padrões de
Engenharia de Software. O sistema permite a gestão de usuários, criação de carteiras virtuais, depósitos, saques e
transferências financeiras Peer-to-Peer (P2P) seguras, garantindo a integridade dos dados e a rastreabilidade das
operações.

Este projeto foi desenvolvido com foco em arquitetura resiliente, segurança na borda e persistência de dados
consistente, refletindo cenários reais da indústria financeira (FinOps e Core Banking).

## Arquitetura e Tecnologias

O ecossistema foi desenhado para ser escalável e de fácil manutenção, utilizando:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.5 (Web, Data JPA, Validation)
* **Segurança:** Spring Security com criptografia BCrypt (Hash de senhas)
* **Banco de Dados:** PostgreSQL (containerizado)
* **Versionamento de Banco:** Flyway Migration
* **Infraestrutura & DevOps:** Docker e provisionamento de ambiente isolado
* **Boas Práticas:** Padrão DTO, Tratamento Global de Exceções (`ControllerAdvice`), Injeção de Dependências e RESTful
  Design.

## Funcionalidades (Roadmap)

### Módulo de Identidade (Concluído)

* [x] Cadastro de novos usuários com validação de dados (Jakarta Validation).
* [x] Proteção de dados sensíveis (Senhas não são salvas em texto plano, utilizando BCrypt).
* [x] Prevenção de duplicidade e inconsistências no banco de dados (Tratamento de conflitos HTTP 409).
* [ ] Autenticação Stateless gerando Token JWT (Em breve).

### Módulo Financeiro (Em breve)

* [ ] Criação automática de carteira atrelada ao usuário.
* [ ] Operações de Cash-In (Depósito) e Cash-Out (Saque).
* [ ] Consulta de saldo em tempo real.

### Módulo de Transações P2P (Em breve)

* [ ] Transferência de valores entre carteiras diferentes.
* [ ] Controle de concorrência e transações ACID no banco de dados para evitar perda financeira ou saldo negativo.
* [ ] Geração de extrato e histórico imutável de movimentações.

---
