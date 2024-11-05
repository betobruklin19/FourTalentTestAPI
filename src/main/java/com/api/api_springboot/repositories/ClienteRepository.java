package com.api.api_springboot.repositories;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Endereco;
import com.api.api_springboot.enums.Estado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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

    public Cliente buscarClientePorId(Long id) {
        String sql = "SELECT c.*, e.rua, e.cidade, e.estado FROM clientes c " +
                "JOIN enderecos e ON c.endereco_id = e.id WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
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
    public void salvarCliente(Cliente cliente) {
        String sqlEndereco = "INSERT INTO enderecos (rua, cidade, estado) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getEndereco().getRua());
            ps.setString(2, cliente.getEndereco().getCidade());
            ps.setString(3, cliente.getEndereco().getEstado().toString());
            return ps;
        }, keyHolder);

        Long enderecoId = keyHolder.getKey().longValue();

        String sqlCliente = "INSERT INTO clientes (nome, email, endereco_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlCliente, cliente.getNome(), cliente.getEmail(), enderecoId);
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
