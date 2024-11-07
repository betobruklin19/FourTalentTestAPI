# API CRUD de Clientes com Endereço

![spring_security_logo_spring_swagger](https://github.com/user-attachments/assets/683fcb7f-4b1b-42ce-95ff-d23839a9127b)







## Descrição

Esta é uma API RESTful desenvolvida com **Spring Boot 3.3.5** e **Java 17** que permite realizar operações CRUD (Criar, Ler, Atualizar e Deletar) em clientes e seus respectivos endereços. A API é protegida por **Spring Security** e utiliza **Swagger** para documentação. Os dados são armazenados em um banco de dados em memória **H2**, e as consultas são realizadas usando **SQL Nativo** com `jdbcTemplate`.

## Tecnologias Utilizadas

- **Spring Boot 3.3.5**
- **Java 17**
- **Spring Security**
- **Swagger**
- **H2 Database**
- **Query/SQL Nativo**

## Funcionalidades

- Cadastro de clientes
- Listagem de clientes
- Atualização de dados dos clientes
- Exclusão de clientes
- Cadastro de endereços relacionados aos clientes
- Documentação interativa da API com Swagger
- Criação e login de usuário autenticado
- Identificação e registro na base de dados de quem realizou a açao de cadastro ou alteração de um cliente assim como sua respectiva data.
- Teste unitário e de integração

### Pré-requisitos

- Java 17
- Maven

### Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/betobruklin19/FourTalentTestAPI.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd FourTalentTestAPI
   ```

3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

### Acessando a API

A API estará disponível em `http://localhost:8080`. Você pode acessar a documentação da API através do Swagger em `http://localhost:8080/swagger-ui.html#/`.

## Exemplos de Uso

### Cadastro de Cliente

```http
POST /clientes
Content-Type: application/json

{
  "nome": "João da Silva",
  "email": "joao@example.com",
  "endereco": {
    "rua": "Rua das Flores",
    "cidade": "São Paulo",
    "estado": "SP"
  }
}
```

### Listar Clientes

```http
GET /clientes
```
### Postman

[collection](https://www.postman.com/betobruklin/geral/collection/e8goucu/api-springboot?action=share&creator=16153036&active-environment=16153036-fde54d44-a11f-4062-91eb-7288b2854b25)

## Swagger

![api_clientes_swagger](https://github.com/user-attachments/assets/1d95192d-64d0-4404-93bb-a315bddae9c3)


## Contribuições

Contribuições são bem-vindas! 


