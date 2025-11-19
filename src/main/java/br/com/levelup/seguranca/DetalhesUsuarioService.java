package br.com.levelup.seguranca;

import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.UsuarioRepositorio;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetalhesUsuarioService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public DetalhesUsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenhaHash())
                .roles("USER") // futuramente dá pra ter ROLE_ADMIN, etc
                .build();
    }
}
