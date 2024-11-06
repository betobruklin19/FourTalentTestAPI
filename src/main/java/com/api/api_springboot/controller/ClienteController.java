package com.api.api_springboot.controller;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes", produces = {"application/json"})
@Tag(name="clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Cria novo cliente", description = "Cria um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo obrigatório faltando"),
            @ApiResponse(responseCode = "409", description = "Cliente já existente")
    })
    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);
            return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
        } catch (ClienteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza um cliente", description = "Atualiza um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Sem conteúdo a retornar"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Cliente informado não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        try {
            clienteService.atualizarCliente(id, cliente);
            return ResponseEntity.ok(cliente);
        } catch (ClienteException e) {
            throw e;
        }
    }

    @Operation(summary = "Deleta um cliente", description = "Deleta um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Cliente informado não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca cliente pelo ID", description = "Busca um cliente existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente informado não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Lista todos os clientes", description = "Busca todos os clientes existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente(s) retornado(s) com sucesso"),
            @ApiResponse(responseCode = "204", description = "Cliente informado não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Lista clientes por estado (UF)", description = "Busca um ou mais clientes por estado (UF)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente(s) retornado(s) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado para o estado informado")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> pesquisarClientesPorEstado(@PathVariable String estado) {
        List<Cliente> clientes = clienteService.pesquisarClientesPorEstado(estado);
        return ResponseEntity.ok(clientes);
    }

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<String> handleClienteNotFound(ClienteException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
