package com.api.api_springboot.repositories;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cliente> listarTodosClientes() {
        String sql = "SELECT c.*, e.rua, e.cidade, e.estado FROM clientes c " +
                "JOIN enderecos e ON c.endereco_id = e.id ORDER BY c.nome";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setEmail(rs.getString("email"));

            Endereco endereco = new Endereco();
            endereco.setId(rs.getLong("endereco_id"));
            endereco.setRua(rs.getString("rua"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setEstado(Estado.valueOf(rs.getString("estado")));
            cliente.setEndereco(endereco);

            return cliente;
        });
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        String sql = "SELECT c.*, e.rua, e.cidade, e.estado FROM clientes c " +
                "JOIN enderecos e ON c.endereco_id = e.id WHERE c.id = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));

                Endereco endereco = new Endereco();
                endereco.setId(rs.getLong("endereco_id"));
                endereco.setRua(rs.getString("rua"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setEstado(Estado.valueOf(rs.getString("estado")));
                c.setEndereco(endereco);

                return c;
            });
            return Optional.of(cliente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Cliente> buscarClientePorEmail(String email) {
        String sql = "SELECT c.* FROM clientes c " +
                "WHERE c.email = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));

                return c;
            });
            return Optional.of(cliente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Cliente> pesquisarClientePorEstado(String estado) {
        String sql = "SELECT c.*, e.rua, e.cidade, e.estado FROM clientes c " +
                "JOIN enderecos e ON c.endereco_id = e.id WHERE e.estado = ?";
        return jdbcTemplate.query(sql, new Object[]{estado}, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setEmail(rs.getString("email"));

            Endereco endereco = new Endereco();
            endereco.setId(rs.getLong("endereco_id"));
            endereco.setRua(rs.getString("rua"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setEstado(Estado.valueOf(rs.getString("estado")));
            cliente.setEndereco(endereco);

            return cliente;
        });
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        String sqlEndereco = "INSERT INTO enderecos (rua, cidade, estado) VALUES (?, ?, ?)";
        KeyHolder keyHolderEndereco  = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getEndereco().getRua());
            ps.setString(2, cliente.getEndereco().getCidade());
            ps.setString(3, cliente.getEndereco().getEstado().toString());
            return ps;
        }, keyHolderEndereco );

        Long enderecoId = keyHolderEndereco.getKey().longValue();
        cliente.getEndereco().setId(enderecoId);

        String sqlCliente = "INSERT INTO clientes (nome, email, endereco_id) VALUES (?, ?, ?)";
        KeyHolder keyHolderCliente = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setLong(3, enderecoId);
            return ps;
        }, keyHolderCliente);

        Long clienteId = keyHolderCliente.getKey().longValue();
        cliente.setId(clienteId); // Define o ID gerado no objeto cliente
        return cliente;
    }

    @Transactional
    public void atualizarCliente(Cliente cliente) {
        String sqlEndereco = "UPDATE enderecos SET rua = ?, cidade = ?, estado = ? WHERE id = ?";
        jdbcTemplate.update(sqlEndereco, cliente.getEndereco().getRua(), cliente.getEndereco().getCidade(), cliente.getEndereco().getEstado(), cliente.getEndereco().getId());
        String sqlCliente = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sqlCliente, cliente.getNome(), cliente.getEmail(), cliente.getId());
    }

    @Transactional
    public void deletarCliente(Long id) {
        Long enderecoId = jdbcTemplate.queryForObject("SELECT endereco_id FROM clientes WHERE id = ?", new Object[]{id}, Long.class);
        jdbcTemplate.update("DELETE FROM clientes WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM enderecos WHERE id = ?", enderecoId);
    }
}
