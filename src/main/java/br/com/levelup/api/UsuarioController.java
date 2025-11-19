package br.com.levelup.api;

import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.UsuarioRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepositorio usuarioRepositorio,
                             PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        // Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenhaHash());
        usuario.setSenhaHash(senhaCriptografada);

        Usuario salvo = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(salvo);
    }
}
