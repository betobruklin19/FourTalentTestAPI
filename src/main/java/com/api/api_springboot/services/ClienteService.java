package com.api.api_springboot.services;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente){
        clienteRepository.salvarCliente(cliente);
        return cliente;
    }

    public Cliente atualizarCliente(Long id, Cliente cliente) {
        cliente.setId(id);
        clienteRepository.atualizarCliente(cliente);
        return cliente;
    }

    public void deletarCliente(Long id) {
        clienteRepository.deletarCliente(id);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.buscarClientePorId(id);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.listarTodosClientes();
    }

    public List<Cliente> pesquisarClientesPorEstado(String estado) {
        return clienteRepository.pesquisarClientePorEstado(estado);
    }
}
