package com.api.api_springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.api.api_springboot.controller.ClienteController;
import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.repositories.ClienteRepository;
import com.api.api_springboot.services.ClienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void deveRetornarClienteQuandoIdValido() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado(Estado.SP); // Exemplo de estado

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente teste");
        cliente.setEmail("cliente.teste@example.com");
        cliente.setEndereco(endereco);

        clienteService.cadastrarCliente(cliente);

        when(clienteService.buscarClientePorId(cliente.getId())).thenReturn(cliente);

        mockMvc.perform(get("/clientes/" + cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente teste"))
                .andExpect(jsonPath("$.id").value(cliente.getId()));
    }

    @Test
    public void deveRetornarNotFoundQuandoClienteNaoExiste() throws Exception {
        when(clienteService.buscarClientePorId(999L)).thenThrow(new ClienteException("Cliente não encontrado com o ID: 999"));

        mockMvc.perform(get("/clientes/999"))
                .andExpect(status().isNotFound());
    }
}
