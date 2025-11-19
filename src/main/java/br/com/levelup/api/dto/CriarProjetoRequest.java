package br.com.levelup.api.dto;

import br.com.levelup.dominio.Projeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CriarProjetoRequest {

    @NotBlank(message = "O nome do projeto é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do projeto deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "O tipo do projeto é obrigatório")
    private Projeto.TipoProjeto tipo; // enum

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Projeto.TipoProjeto getTipo() {
        return tipo;
    }

    public void setTipo(Projeto.TipoProjeto tipo) {
        this.tipo = tipo;
    }
}
