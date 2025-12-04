📚 BookStore — Sistema Web em Java (Spring Boot)

Projeto desenvolvido para a disciplina de Teste de Software, com o objetivo de servir como base para aplicação de métricas (Chidamber & Kemerer, Lorenz & Kidd), análise de complexidade ciclomática, criação de testes unitários (JUnit + Mockito), cobertura de código e testes funcionais com Selenium.

🚀 Tecnologias Utilizadas

Java 17

Spring Boot 3.x

Spring Web (API REST)

Spring Data JPA

H2 Database

Thymeleaf

JUnit 5 / Mockito

Selenium (testes funcionais)

📦 Estrutura do Projeto
src/main/java/com/example/bookstore/
 ├── controller/   → Controllers REST
 ├── model/        → Classes de domínio (10+ entidades)
 ├── repository/   → Repositórios JPA
 ├── service/      → Regras de negócio
 └── dto/          → Objetos de transferência (DTOs)

📘 Funcionalidades Principais

Cadastro de livros, categorias, autores, usuários, pedidos e avaliações

API REST para:

listar livros

buscar livros

recomendar livros (lógica complexa)

processar pedidos

Métodos com alta complexidade ciclomática, ideais para estudo e testes

Suporte para testes unitários, mocks e testes funcionais

▶️ Como Executar o Projeto

Certifique-se de ter Java 17+ e Maven instalados.

No terminal, execute:

mvn spring-boot:run


Acesse:

Página inicial:
http://localhost:8080

API de livros:
http://localhost:8080/api/books

API de pedidos:
http://localhost:8080/api/orders/process
