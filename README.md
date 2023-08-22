# Locadora de Veículos - Sistema de Locação de Veículos

Este repositório contém o código fonte do projeto de uma Locadora de Veículos, desenvolvido utilizando as tecnologias Spring Boot, JPA e MySQL. O objetivo principal desse projeto é implementar um sistema de aluguel de veículos.

## Colaboradores

- [@p-oliveira7](https://github.com/p-oliveira7)
- [@vinicchaves](https://github.com/vinicchaves)
- [@jcfbernardo](https://github.com/jcfbernardo)

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## Visão Geral do Projeto

O projeto tem como objetivo criar uma plataforma de locação de veículos. Através desta plataforma, os usuários poderão navegar pelo sistema e fazer a locação de veículos de diversos fabricantes.

### Pré-requisitos

- Java Development Kit (JDK) versão 20 ou superior
- Maven
- MySQL Server
  
 ### Passos para Execução

1. Clone o repositório:

```
git clone https://github.com/p-oliveira7/desafio-locadora-solutis.git
```
2. Acesse o diretório do projeto:

```
cd locadora
```

3. Crie um banco de dados MySQL chamado `locadora`:

```sql
CREATE DATABASE locadora;
```

4. Configure as informações de conexão com o banco de dados no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/locadora
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

5. Execute o aplicativo Spring Boot:

```bash
mvn spring-boot:run
```

## Estrutura do Projeto

O projeto está estruturado da seguinte forma:

- `src/main/java/br/com/locadora/api`: Contém as classes Java do projeto, incluindo controladores, serviços e modelos.
- `src/main/resources`: Contém os recursos, como arquivos de configuração e templates.

## Dependências

O arquivo `pom.xml` especifica as dependências do projeto. As principais dependências utilizadas são:
Spring Boot: O projeto está sendo construído usando o framework Spring Boot, que é uma estrutura para simplificar o desenvolvimento de aplicativos Java.

- `Spring Boot Starter Data JPA`: Essa dependência facilita a integração do JPA (Java Persistence API) com o Spring Boot, permitindo a manipulação de dados usando abstrações de alto nível.
- `Spring Boot Starter Validation`: Essa dependência fornece suporte para validação de dados em seu aplicativo, garantindo que os dados de entrada estejam corretos e coerentes.
- `Spring Boot Starter Web`: Essa dependência oferece suporte ao desenvolvimento de aplicativos da web usando o Spring MVC (Model-View-Controller) no contexto do Spring Boot.
- `Spring Boot DevTool`s: Uma ferramenta que auxilia no desenvolvimento, oferecendo recursos como reinicialização automática e atualizações rápidas durante o desenvolvimento.
- `MySQL Connector/J`: Essa é uma biblioteca que permite a conexão e interação com um banco de dados MySQL.
- `Project Lombok`: Lombok é uma biblioteca que simplifica a criação de classes Java, gerando automaticamente getters, setters, construtores e outros métodos comuns.
- `Spring Boot Starter Test`: Essa dependência fornece suporte para testes no contexto do Spring Boot.
- `Spring Boot Starter Security`: Essa dependência é usada para integrar recursos de segurança no aplicativo, como autenticação e autorização.
- `Spring Security Test`: Fornece suporte para testes de segurança no contexto do Spring.
- `Java JWT (Auth0)`: Essa biblioteca é usada para trabalhar com tokens JWT (JSON Web Tokens) para autenticação e autorização.
- `MapStruct`: É uma biblioteca que simplifica a conversão entre objetos Java de diferentes tipos, principalmente usada para mapeamento de DTOs (Data Transfer Objects).
- `SpringDoc OpenAPI`: Essa dependência adiciona suporte à geração automática de documentação OpenAPI (anteriormente conhecida como Swagger) para sua API.
- `Logback Classic`: Uma biblioteca de logging para registrar eventos e informações relevantes do aplicativo.
- `JetBrains Annotations`: Essas anotações são usadas para melhorar a análise de código por meio de anotações de linguagem, usadas principalmente pela IDE IntelliJ IDEA.
