package com.api.api_springboot.services;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.buscarClientePorEmail(cliente.getEmail());
        if (clienteExistente.isPresent()) {
            throw new ClienteException("Cliente já cadastrado!");
        }
        return clienteRepository.salvarCliente(cliente);
    }

    public Cliente atualizarCliente(Long id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.buscarClientePorId(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado para atualização"));
        cliente.setId(id);
        clienteRepository.atualizarCliente(cliente);
        return cliente;
    }

    public void deletarCliente(Long id) {
        Cliente cliente = clienteRepository.buscarClientePorId(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado para exclusão"));
        clienteRepository.deletarCliente(cliente.getId());
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.buscarClientePorId(id)
                .orElseThrow(() -> new ClienteException("Cliente não encontrado com o ID: " + id));
    }

    public List<Cliente> listarTodosClientes() {
        List<Cliente> clientes = clienteRepository.listarTodosClientes();
        if (clientes.isEmpty()) {
            throw new ClienteException("Nenhum cliente encontrado.");
        }
        return clientes;
    }

    public List<Cliente> pesquisarClientesPorEstado(String estado) {
        List<Cliente> clientes = clienteRepository.pesquisarClientePorEstado(estado);
        if (clientes.isEmpty()) {
            throw new ClienteException("Nenhum cliente encontrado para o estado: " + estado);
        }
        return clientes;
    }
}
