package br.com.locadora.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import br.com.locadora.api.domain.usuario.AuthDTO;
import br.com.locadora.api.domain.usuario.RegisterDTO;
import br.com.locadora.api.domain.usuario.Usuario;
import br.com.locadora.api.domain.usuario.UsuarioRepository;
import br.com.locadora.api.infra.security.TokenDTO;
import br.com.locadora.api.infra.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Efetuar login")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> efetuarLogin(
            @RequestBody @Valid AuthDTO dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var authentication = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            logger.error("Erro ao registrar novo usuário: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Registrar um novo usuário")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterDTO data) {
        try{
        if (usuarioRepository.findByEmail(data.email()) != null) {
            logger.warn("Aviso: O email '" + data.email() + "' já está cadastrado");
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.email(), encryptedPassword);

        usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao registrar novo usuário: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
