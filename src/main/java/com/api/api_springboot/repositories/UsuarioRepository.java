package com.api.api_springboot.repositories;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.api.api_springboot.entities.Usuario;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Usuario> CriarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (login, password) VALUES (?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, usuario.getUsername());
                ps.setString(2, usuario.getPassword());
                return ps;
            });
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Usuario BuscarUsuarioPorLogin(String login) {
        String sql = "SELECT u.* FROM usuarios u WHERE u.login = ?";
        List<Usuario> usuarios = jdbcTemplate.query(sql, new Object[]{login}, (rs, rowNum) -> {
            Usuario u = new Usuario();
            u.setId(rs.getLong("id"));
            u.setLogin(rs.getString("login"));
            u.setPassword(rs.getString("password"));
            return u;
        });
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

}