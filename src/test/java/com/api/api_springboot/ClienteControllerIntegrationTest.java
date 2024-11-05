package com.api.api_springboot;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import com.api.api_springboot.repositories.ClienteRepository;
import com.api.api_springboot.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Test
    void cadastrarCliente_retornaClienteCriado() throws Exception {
        String clienteJson = "{\"nome\":\"João\",\"email\":\"joao@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("João")))
                .andExpect(jsonPath("$.email", is("joao@example.com")));
    }

    @Test
    void buscarClientePorId_clienteExistente_retornaCliente() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua B");
        endereco.setCidade("Cidade Y");
        endereco.setEstado(Estado.SP);

        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@example.com");
        cliente.setEndereco(endereco);
        clienteService.cadastrarCliente(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria")))
                .andExpect(jsonPath("$.email", is("maria@example.com")));
    }

    @Test
    void listarTodosClientes_retornaListaDeClientes() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua C");
        endereco.setCidade("Cidade Z");
        endereco.setEstado(Estado.SP);

        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@example.com");
        cliente.setEndereco(endereco);
        clienteRepository.salvarCliente(cliente);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));
    }

    @Test
    void atualizarCliente_clienteExistente_atualizaCliente() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua D");
        endereco.setCidade("Cidade W");
        endereco.setEstado(Estado.SP);

        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setEmail("carlos@example.com");
        cliente.setEndereco(endereco);
        clienteRepository.salvarCliente(cliente);

        String clienteAtualizadoJson = "{\"nome\":\"Carlos Atualizado\",\"email\":\"carlosatualizado@example.com\"," +
                "\"endereco\":{\"rua\":\"Rua D\",\"cidade\":\"Cidade W\",\"estado\":\"SP\"}}";


        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/" + cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Carlos Atualizado")))
                .andExpect(jsonPath("$.email", is("carlosatualizado@example.com")));
    }

    @Test
    void deletarCliente_clienteExistente_retornaNoContent() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua D");
        endereco.setCidade("Cidade W");
        endereco.setEstado(Estado.SP);

        Cliente cliente = new Cliente();
        cliente.setNome("Laura");
        cliente.setEmail("laura@example.com");
        cliente.setEndereco(endereco);
        clienteRepository.salvarCliente(cliente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/" + cliente.getId()))
                .andExpect(status().isNoContent());
    }
}
