package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public class UsuarioLoja extends Usuario {
    @CNPJ
    private String CNPJ;

    @OneToMany(mappedBy = "Agendamento", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
