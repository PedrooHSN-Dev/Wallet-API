<div align="center">

# Secure Wallet API

### API REST de carteira digital com foco em segurança e boas práticas

[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=flat&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)](https://www.postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)](https://www.docker.com)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat)](LICENSE)

</div>

---

## Sobre o projeto

A **Secure Wallet API** é o backend de uma carteira digital (e-wallet) construída com padrões de engenharia de software aplicados a cenários reais do setor financeiro. O sistema permite cadastro de usuários, criação de carteiras virtuais, depósitos, saques e transferências P2P seguras com rastreabilidade completa das operações.

O projeto foi desenvolvido com foco em segurança na borda, arquitetura resiliente e persistência consistente.

---

## Arquitetura

```
src/main/java/com/securewallet/walletapi/
├── config/
│   └── SecurityConfig.java        # Spring Security + sessão stateless
├── controller/
│   └── UserController.java        # Endpoints REST
├── domain/
│   └── User.java                  # Entidade JPA com externalId UUID
├── dto/
│   ├── UserRegistrationRequestDTO.java   # Validação na entrada
│   └── UserResponseDTO.java              # Sem expor dados sensíveis
├── exception/
│   └── GlobalExceptionHandler.java  # Tratamento centralizado de erros
├── repository/
│   └── UserRepository.java
└── service/
    └── UserService.java           # Regras de negócio + BCrypt
```

**Decisões de design:**
- `externalId` (UUID) exposto na API — o `id` interno nunca sai do banco
- Senha nunca retornada em nenhum DTO de resposta
- `@NoArgsConstructor(access = PROTECTED)` na entidade — criação controlada pelo construtor de domínio
- `ResponseStatusException` para mapeamento semântico de erros HTTP

---

## Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.5 |
| Segurança | Spring Security + BCrypt + JWT (em andamento) |
| Persistência | Spring Data JPA / Hibernate |
| Banco de dados | PostgreSQL |
| Migração | Flyway |
| Containerização | Docker + Docker Compose |
| Build | Maven (Maven Wrapper incluído) |

---

## Funcionalidades

### Módulo de Identidade ✅
- [x] Cadastro com validação de dados (Jakarta Validation)
- [x] Hash de senha com BCrypt — nunca salvo em texto plano
- [x] Prevenção de duplicidade com retorno HTTP 409
- [x] UUID externo exposto — ID interno protegido
- [ ] Autenticação stateless com JWT *(em desenvolvimento)*

### Módulo Financeiro *(em breve)*
- [ ] Criação automática de carteira ao cadastrar usuário
- [ ] Depósito e saque com validação de saldo
- [ ] Consulta de saldo em tempo real

### Módulo de Transações P2P *(em breve)*
- [ ] Transferência entre carteiras com controle transacional (ACID)
- [ ] Extrato com histórico imutável de movimentações
- [ ] Concorrência controlada para evitar race conditions em transferências

---

## Como executar

### Pré-requisitos

- Java 21+
- Docker e Docker Compose

### Com Docker (recomendado)

```bash
# Clone o repositório
git clone https://github.com/PedrooHSN-Dev/wallet-api.git
cd wallet-api

# Suba o banco de dados
docker compose up -d db

# Execute a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Variáveis de ambiente

Crie um arquivo `src/main/resources/application.yml` (ignorado pelo git por segurança):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/securewallet
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    enabled: true

security:
  jwt:
    secret: sua_chave_secreta_local
    expiration: 86400000
```

---

## Endpoints

```
POST  /users          → Cadastro de novo usuário
```

> Autenticação via JWT em desenvolvimento. Novos endpoints serão adicionados nos módulos financeiro e de transações.

### Exemplo de uso

**Cadastro:**
```json
POST /users
Content-Type: application/json

{
  "fullName": "Pedro Henrique",
  "email": "pedro@email.com",
  "password": "minhasenha123"
}
```

**Resposta (201 Created):**
```json
{
  "externalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "fullName": "Pedro Henrique",
  "email": "pedro@email.com"
}
```

**Conflito (409):**
```json
{
  "timestamp": "2026-01-15T10:30:00",
  "status": 409,
  "message": "Este e-mail já está cadastrado na nossa base."
}
```

---

## Rodando os testes

```bash
./mvnw test
```

---

## Licença

Distribuído sob a licença MIT. Veja [LICENSE](LICENSE) para mais informações.

---

<div align="center">
  <sub>Desenvolvido por <a href="https://github.com/PedrooHSN-Dev">Pedro Henrique</a></sub>
</div>
