package br.com.levelup.dominio;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @ManyToOne(optional = false)
    private Usuario dono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProjeto tipo = TipoProjeto.PESSOAL;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public enum TipoProjeto {
        ESTUDOS,
        TRABALHO,
        PESSOAL
    }

    public Projeto() {
    }

    public Long getId() {
        return id;
    }

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

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public TipoProjeto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProjeto tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
