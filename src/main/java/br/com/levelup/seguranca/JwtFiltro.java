package br.com.levelup.seguranca;

import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFiltro extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepositorio usuarioRepositorio;

    public JwtFiltro(TokenService tokenService, UsuarioRepositorio usuarioRepositorio) {
        this.tokenService = tokenService;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Long usuarioId = tokenService.getUsuarioId(token); // subject = ID

                usuarioRepositorio.findById(usuarioId).ifPresent(usuario -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                            usuario, null, java.util.List.of() // sem roles por enquanto
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });

            } catch (Exception e) {
                System.out.println("JWT inválido: " + e.getMessage());
                // NÃO lança exception, só segue sem autenticar
            }
        }

        filterChain.doFilter(request, response);
    }

    // libera o filtro para login e cadastro
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        // não filtra login e POST /usuarios
        if (path.equals("/auth/login") && "POST".equals(method)) return true;
        if (path.equals("/usuarios") && "POST".equals(method)) return true;

        return false;
    }
}
