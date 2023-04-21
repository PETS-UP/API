package com.petsup.api.entities.usuario;

import org.hibernate.validator.constraints.br.CNPJ;

public class UsuarioLoja extends Usuario {
    @CNPJ
    private String CNPJ;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }
}
