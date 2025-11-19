package br.com.levelup.servico.leitura;

import br.com.levelup.api.dto.ResumoDashboardDTO;
import br.com.levelup.dominio.Usuario;
import br.com.levelup.dominio.repositorio.ProjetoRepositorio;
import br.com.levelup.dominio.repositorio.UsuarioRepositorio;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DashboardLeituraServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final ProjetoRepositorio projetoRepositorio;

    public DashboardLeituraServico(UsuarioRepositorio usuarioRepositorio,
                                   ProjetoRepositorio projetoRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.projetoRepositorio = projetoRepositorio;
    }

    @Cacheable(value = "resumoDashboard", key = "#usuarioId")
    public ResumoDashboardDTO carregarResumo(Long usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        long totalProjetos = projetoRepositorio.count();

        return new ResumoDashboardDTO(
                usuario.getNivel(),
                usuario.getXp(),
                totalProjetos
        );
    }
}
