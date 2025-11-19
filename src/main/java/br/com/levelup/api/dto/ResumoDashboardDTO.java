package br.com.levelup.api.dto;

public class ResumoDashboardDTO {

    private int nivel;
    private long xp;
    private long totalProjetos;

    public ResumoDashboardDTO(int nivel, long xp, long totalProjetos) {
        this.nivel = nivel;
        this.xp = xp;
        this.totalProjetos = totalProjetos;
    }

    public int getNivel() {
        return nivel;
    }

    public long getXp() {
        return xp;
    }

    public long getTotalProjetos() {
        return totalProjetos;
    }
}
