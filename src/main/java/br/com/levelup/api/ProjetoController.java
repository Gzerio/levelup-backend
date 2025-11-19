package br.com.levelup.api;

import br.com.levelup.api.dto.CriarProjetoRequest;
import br.com.levelup.dominio.Projeto;
import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.ProjetoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoRepositorio projetoRepositorio;

    public ProjetoController(ProjetoRepositorio projetoRepositorio) {
        this.projetoRepositorio = projetoRepositorio;
    }


    @GetMapping
    public List<Projeto> listar(Authentication auth) {
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

    
    return projetoRepositorio.findByDonoId(usuarioLogado.getId());
    }

    @PostMapping
public ResponseEntity<Projeto> criar(@RequestBody @jakarta.validation.Valid CriarProjetoRequest request,
                                     Authentication auth) {

    Usuario dono = (Usuario) auth.getPrincipal();

    Projeto projeto = new Projeto();
    projeto.setNome(request.getNome());
    projeto.setDescricao(request.getDescricao());
    projeto.setTipo(request.getTipo());
    projeto.setDono(dono);
    projeto.setCriadoEm(LocalDateTime.now());

    Projeto salvo = projetoRepositorio.save(projeto);
    return ResponseEntity.ok(salvo);
}

}
