package br.com.levelup.dominio.repositorio;

import br.com.levelup.dominio.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

    // Vai ser usado em /projetos/meus
    List<Projeto> findByDonoId(Long donoId);
}
