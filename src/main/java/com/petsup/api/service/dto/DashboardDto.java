package com.petsup.api.service.dto;

public class DashboardDto {
    private int diasMaisAtivos;

    private int agendamentosNoDia;

    private int quantidadeAgendamentosPorServico;

    private double mediaAvaliacao;

    public int getDiasMaisAtivos() {
        return diasMaisAtivos;
    }

    public void setDiasMaisAtivos(int diasMaisAtivos) {
        this.diasMaisAtivos = diasMaisAtivos;
    }

    public int getAgendamentosNoDia() {
        return agendamentosNoDia;
    }

    public void setAgendamentosNoDia(int agendamentosNoDia) {
        this.agendamentosNoDia = agendamentosNoDia;
    }

    public int getQuantidadeAgendamentosPorServico() {
        return quantidadeAgendamentosPorServico;
    }

    public void setQuantidadeAgendamentosPorServico(int quantidadeAgendamentosPorServico) {
        this.quantidadeAgendamentosPorServico = quantidadeAgendamentosPorServico;
    }

    public double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }
}
