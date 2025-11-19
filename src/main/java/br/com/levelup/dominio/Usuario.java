package br.com.levelup.dominio;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    private Integer nivel = 1;

    private Long xp = 0L;

    private Integer diasStreak = 0;

    private LocalDate dataUltimaAtividade;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public Usuario() {
    }

    public Usuario(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Integer getDiasStreak() {
        return diasStreak;
    }

    public void setDiasStreak(Integer diasStreak) {
        this.diasStreak = diasStreak;
    }

    public LocalDate getDataUltimaAtividade() {
        return dataUltimaAtividade;
    }

    public void setDataUltimaAtividade(LocalDate dataUltimaAtividade) {
        this.dataUltimaAtividade = dataUltimaAtividade;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
