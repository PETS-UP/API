package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
public class UsuarioLoja extends Usuario {
    @CNPJ
    private String CNPJ;

//    @OneToMany(mappedBy = "fk_loja", fetch = FetchType.LAZY)
//    private List<Agendamento> agendamentos;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

}
