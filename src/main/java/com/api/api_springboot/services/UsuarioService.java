package com.api.api_springboot.services;

import com.api.api_springboot.entities.Cliente;
import com.api.api_springboot.entities.Usuario;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.repositories.ClienteRepository;
import com.api.api_springboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioCriado = usuarioRepository.CriarUsuario(usuario);
        if (usuarioCriado.isPresent()) {
            throw new ClienteException("Usuário já cadastrado!");
        }
        return usuarioRepository.CriarUsuario(usuario);
    }

}
