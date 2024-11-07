package com.api.api_springboot.services;

import com.api.api_springboot.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api_springboot.entities.Usuario;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfig securityConfig;

    public void cadastrarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.BuscarUsuarioPorLogin(usuario.getUsername());
        if (usuarioExistente != null) {
            throw new ClienteException("Usuário já cadastrado!");
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(usuario.getUsername());
        var encodedPassword = this.securityConfig.passwordEncoder().encode(usuario.getPassword());
        novoUsuario.setPassword(encodedPassword);
        usuarioRepository.CriarUsuario(novoUsuario);
    }

}
