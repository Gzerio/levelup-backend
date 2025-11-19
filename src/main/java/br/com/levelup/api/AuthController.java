package br.com.levelup.api;

import br.com.levelup.api.dto.LoginRequest;
import br.com.levelup.api.dto.LoginResponse;
import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.UsuarioRepositorio;
import br.com.levelup.seguranca.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepositorio usuarioRepositorio;

    public AuthController(AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          UsuarioRepositorio usuarioRepositorio) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {

        // dispara o fluxo de autenticação do Spring Security
        var authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha()
        );
        authenticationManager.authenticate(authToken);

        // se chegou aqui, credenciais são válidas
        Usuario usuario = usuarioRepositorio.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
