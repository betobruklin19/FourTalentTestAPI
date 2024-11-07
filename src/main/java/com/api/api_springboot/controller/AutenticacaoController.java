package com.api.api_springboot.controller;

import com.api.api_springboot.entities.Usuario;
import com.api.api_springboot.exceptions.ClienteException;
import com.api.api_springboot.records.Login;
import com.api.api_springboot.services.TokenService;
import com.api.api_springboot.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Endpoints para criação de usuário e autenticação")
@RestController
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Cria um usuário de autenticação", description = "Cria um usuário de autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo obrigatório faltando. Verifique se todos os campos obrigatórios estão preenchidos."),
            @ApiResponse(responseCode = "409", description = "Usuário já existe. Não é possível criar um usuário com um login já existente"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor. Não foi possível criar o usuário. Tente novamente mais tarde.")
    })
    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody Usuario usuario) {
        try {
            usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ClienteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza o login de autenticação", description = "Realiza o login de autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Credenciais de usuário inválidas."),
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Login login) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.login(),
                            login.password()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String password = userDetails.getPassword();

            Usuario usuario = new Usuario();
            usuario.setLogin(username);
            usuario.setPassword(password);

            String jwt = tokenService.gerarToken(usuario);

            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(401).body("Usuário ou senha incorretas.");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Erro na autenticação.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor.");
        }
    }
}
