package com.api.api_springboot;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.repositories.ClienteRepository;
import com.api.api_springboot.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceUnitTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setEmail("email@exemplo.com");

        when(clienteRepository.buscarClientePorEmail(cliente.getEmail())).thenReturn(Optional.empty());

        when(clienteRepository.salvarCliente(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.cadastrarCliente(cliente);

        assertNotNull(resultado);
        verify(clienteRepository, times(1)).salvarCliente(cliente);
    }


    @Test
    void cadastrarClienteConflitoJaExistente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("email@exemplo.com");

        when(clienteRepository.buscarClientePorEmail(cliente.getEmail())).thenReturn(Optional.of(cliente));

        assertThrows(ClienteException.class, () -> clienteService.cadastrarCliente(cliente));
        verify(clienteRepository, never()).salvarCliente(cliente);
    }

    @Test
    void atualizarCliente() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setEmail("email@exemplo.com");
        clienteExistente.setEndereco(new Endereco());

        when(clienteRepository.buscarClientePorId(1L)).thenReturn(Optional.of(clienteExistente));

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setEmail("novoemail@exemplo.com");
        clienteAtualizado.setEndereco(new Endereco());

        doNothing().when(clienteRepository).atualizarCliente(clienteAtualizado);

        Cliente resultado = clienteService.atualizarCliente(1L, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("novoemail@exemplo.com", resultado.getEmail());
        verify(clienteRepository, times(1)).atualizarCliente(clienteAtualizado);
    }


    @Test
    void buscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.buscarClientePorId(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = Optional.ofNullable(clienteService.buscarClientePorId(1L));

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(clienteRepository, times(1)).buscarClientePorId(1L);
    }

    @Test
    void deletarCliente() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setEmail("email@exemplo.com");

        when(clienteRepository.buscarClientePorId(1L)).thenReturn(Optional.of(clienteExistente));

        doNothing().when(clienteRepository).deletarCliente(1L);

        clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).deletarCliente(1L);
    }

}
