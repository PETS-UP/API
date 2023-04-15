package com.petsup.api.entities.usurario;

import org.hibernate.validator.constraints.br.CPF;

public class UsuarioDonoPet extends Usuario {
    @CPF
    private String CPF;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
