package com.api.api_springboot;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import com.api.api_springboot.repositories.ClienteRepository;
import com.api.api_springboot.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        String loginJson = "{\"login\":\"betobruklin\",\"password\":\"12345678\"}";

        jwtToken = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


    @Test
    void cadastrarCliente_retornaClienteCriado() throws Exception {
        String clienteJson = "{\"nome\":\"João\",\"email\":\"joao@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("João")))
                .andExpect(jsonPath("$.email", is("joao@example.com")));
    }

    @Test
    void buscarClientePorId_clienteExistente_retornaCliente() throws Exception {
        String clienteJson = "{\"nome\":\"Maria\",\"email\":\"maria@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .content(clienteJson));

        Optional<Cliente> cliente = clienteRepository.buscarClientePorEmail("maria@example.com");

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + cliente.get().getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria")))
                .andExpect(jsonPath("$.email", is("maria@example.com")));
    }

    @Test
    void listarTodosClientes_retornaListaDeClientes() throws Exception {
        String clienteJson = "{\"nome\":\"Carlos\",\"email\":\"carlos@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(clienteJson));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));
    }

    @Test
    void atualizarCliente_clienteExistente_atualizaCliente() throws Exception {
        String clienteJson = "{\"nome\":\"Josafa\",\"email\":\"josafa@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(clienteJson));

        Optional<Cliente> cliente = clienteRepository.buscarClientePorEmail("josafa@example.com");


        String clienteAtualizadoJson = "{\"nome\":\"Josafa Atualizado\",\"email\":\"josafaatualizado@example.com\"," +
                "\"endereco\":{\"rua\":\"Rua D\",\"cidade\":\"Cidade W\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/" + cliente.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .content(clienteAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Josafa Atualizado")))
                .andExpect(jsonPath("$.email", is("josafaatualizado@example.com")));
    }

    @Test
    void deletarCliente_clienteExistente_retornaNoContent() throws Exception {
        String clienteJson = "{\"nome\":\"Cornelio\",\"email\":\"cornelio@example.com\",\"endereco\":{\"rua\":\"Rua A\",\"cidade\":\"Cidade X\",\"estado\":\"SP\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(clienteJson));

        Optional<Cliente> cliente = clienteRepository.buscarClientePorEmail("cornelio@example.com");

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/" + cliente.get().getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                )
                .andExpect(status().isNoContent());
    }
}
