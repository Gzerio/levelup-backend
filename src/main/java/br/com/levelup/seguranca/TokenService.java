package br.com.levelup.seguranca;

import br.com.levelup.dominio.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    // vem de app.jwt.secret = ${JWT_SECRET:dev-secret-levelup}
    @Value("${app.jwt.secret}")
    private String segredo;

    // tempo de expiração em minutos (config em application.properties)
    @Value("${app.jwt.expiration-minutes:60}")
    private long expirationMinutes;

    public String gerarToken(Usuario usuario) {
        Algorithm alg = Algorithm.HMAC256(segredo);

        Instant expiresAt = Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("levelup-api")
                .withSubject(usuario.getId().toString()) // ID do usuário no subject
                .withExpiresAt(expiresAt)
                .sign(alg);
    }

    public Long getUsuarioId(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(segredo);
            var decoded = JWT.require(alg)
                    .withIssuer("levelup-api")
                    .build()
                    .verify(token);

            return Long.parseLong(decoded.getSubject());
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }
}
