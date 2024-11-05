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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes", produces = {"application/json"})
@Tag(name="clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.cadastrarCliente(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (ClienteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        clienteService.atualizarCliente(id, cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Busca cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> pesquisarClientesPorEstado(@PathVariable String estado) {
        List<Cliente> clientes = clienteService.pesquisarClientesPorEstado(estado);
        return ResponseEntity.ok(clientes);
    }
}
