package com.api.api_springboot.repositories;

import com.api.api_springboot.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Usuario> CriarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (email, senha) VALUES (?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, usuario.getUsername());
                ps.setString(2, usuario.getPassword());
                return ps;
            });
            return Optional.of(usuario);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Usuario> BuscarUsuarioPorEmail(String email){
        String sql = "SELECT u.* FROM usuarios u WHERE u.email = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setUsername(rs.getString("email"));
                u.setPassword(rs.getString("senha"));
                return u;
            });
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
