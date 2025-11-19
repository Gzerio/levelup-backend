# LevelUp Backend 🎮📚

Backend em **Spring Boot** para o LevelUp – uma API de estudos/tarefas gamificada, onde o usuário ganha **XP**, sobe de **nível** e organiza seus **projetos de estudo**.

Pensado para ser um projeto de portfólio **limpo, seguro e pronto para produção**, com autenticação JWT, PostgreSQL e boas práticas de arquitetura.

---

## 🚀 Tecnologias

- **Java 21+** (projeto usando toolchain Java 25)
- **Spring Boot 3**
  - Spring Web
  - Spring Data JPA
  - Spring Security + JWT
  - Bean Validation (Jakarta Validation)
  - Cache (Caffeine)
- **PostgreSQL**
- **Gradle** (wrapper incluso)

---

## 🧱 Domínio principal

### Usuário (`Usuario`)
- `id`
- `nome`
- `email` (único)
- `senhaHash` (BCrypt)
- `nivel`
- `xp`
- `diasStreak`
- `dataUltimaAtividade`
- `criadoEm`

### Projeto (`Projeto`)
- `id`
- `nome`
- `descricao`
- `tipo` (enum `PESSOAL`, `ESTUDOS`, etc.)
- `dono` (relacionamento com `Usuario`)
- `criadoEm`

---

## 🏗️ Arquitetura

Camadas separadas por responsabilidade:

- `api`  
  Controllers REST (ex.: `AuthController`, `UsuarioController`, `ProjetoController`).
- `api.dto`  
  Objetos de entrada e saída (requests/responses) com **Bean Validation**.
- `dominio`  
  Entidades JPA (`Usuario`, `Projeto`) e regras de negócio do domínio.
- `dominio.repositorio`  
  Repositórios Spring Data JPA.
- `servico`  
  Serviços de leitura/uso da aplicação (ex.: dashboard).
- `seguranca`  
  `TokenService` (JWT), `JwtFiltro`, `DetalhesUsuarioService`.
- `config`  
  Config de segurança, cache, CORS, etc.

---

## 🔒 Segurança

- **Autenticação** via **JWT** (`Bearer <token>`).
- Senhas armazenadas com **BCrypt**.
- **Spring Security** protegendo todos os endpoints (exceto login e cadastro).
- **Stateless**: sem sessão em servidor (JWT puro).
- **Bean Validation** nos DTOs:
  - `LoginRequest` – validação de e-mail e senha.
  - `CriarProjetoRequest` – validação de nome, descrição e tipo.
- **CORS configurado** para front rodando em `localhost` (3000, 5173, etc.).

---

## ⚙️ Configuração do ambiente

### 1. Requisitos

- Java 21+  
- PostgreSQL rodando localmente  
- Git  
- (Opcional) Postman/Insomnia pra testar a API

### 2. Banco de dados

Exemplo de criação de banco e usuário **com permissões limitadas**:

```sql
CREATE DATABASE levelup;

CREATE USER levelup WITH ENCRYPTED PASSWORD 'levelup123';

GRANT CONNECT ON DATABASE levelup TO levelup;
GRANT USAGE ON SCHEMA public TO levelup;

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO levelup;
