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
- **SQL Nativo com jdbcTemplate**

## Funcionalidades

- Cadastro de clientes
- Listagem de clientes
- Atualização de dados dos clientes
- Exclusão de clientes
- Cadastro de endereços relacionados aos clientes
- Documentação interativa da API com Swagger
- Identificação e registro na base de dados de quem realizou a açao de cadastro ou alteração de um cliente assim como sua respectiva data.

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

A API estará disponível em `http://localhost:8080`. Você pode acessar a documentação da API através do Swagger em `http://localhost:8080/swagger-ui.html`.

## Exemplos de Uso

### Cadastro de Cliente

```http
POST /clientes
Content-Type: application/json

{
  "nome": "João da Silva",
  "email": "joao@example.com",
  "cpf": "12345678909",
  "idade": 30,
  "endereco": {
    "rua": "Rua das Flores",
    "numero": "123",
    "cidade": "São Paulo",
    "estado": "SP"
  }
}
```

### Listar Clientes

```http
GET /clientes
```

## Contribuições

Contribuições são bem-vindas! 


