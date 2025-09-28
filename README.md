# Vida Financeira API

![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)

API para ajudar a manter a saúde financeira, permitindo o gerenciamento de receitas e despesas pessoais.

## ✨ Features

-   Autenticação de usuários com JWT (Registro e Login).
-   CRUD completo para Receitas.
-   CRUD completo para Despesas.
-   Geração de relatórios mensais.
-   Geração de relatórios de despesas por categoria.
-   Documentação da API com Swagger.

## 🚀 Tecnologias Utilizadas

-   **Backend:**
    -   [Java 21](https://www.oracle.com/java/technologies/javase/21-relnote-issues.html)
    -   [Spring Boot 3](https://spring.io/projects/spring-boot)
    -   [Spring Security](https://spring.io/projects/spring-security)
    -   [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
    -   [Hibernate](https://hibernate.org/)
    -   [Lombok](https://projectlombok.org/)
-   **Banco de Dados:**
    -   [H2 Database](https://www.h2database.com/html/main.html) (em memória)
-   **Autenticação:**
    -   [JWT (JSON Web Token)](https://jwt.io/)
-   **Documentação:**
    -   [Springdoc OpenAPI (Swagger)](https://springdoc.org/)
-   **Build:**
    -   [Apache Maven](https://maven.apache.org/)

## 💻 Como Começar

### Pré-requisitos

-   Java 21 ou superior.
-   Maven 3.9 ou superior.

### Instalação e Execução

1.  Clone o repositório:
    ```bash
    git clone https://github.com/rafaelclima/vidaFinanceira.git
    ```
2.  Navegue até o diretório do projeto:
    ```bash
    cd vidaFinanceira
    ```
3.  Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```
A API estará disponível em `http://localhost:8080`.

## 📖 Documentação da API

A documentação completa da API, com todos os endpoints e modelos de dados, está disponível via Swagger UI.

Após iniciar a aplicação, acesse:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---
