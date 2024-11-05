# API CRUD de Clientes com Endereço

![Spring Boot](https://spring.io/img/homepage/boot-reboot.png)

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

## Configuração do Ambiente

### Pré-requisitos

- Java 17
- Maven

### Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd seu-repositorio
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

Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou um issue.

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Sinta-se à vontade para personalizar as seções de acordo com suas necessidades, adicionar mais exemplos ou detalhes conforme necessário. Se precisar de imagens específicas ou alterações, me avise!
