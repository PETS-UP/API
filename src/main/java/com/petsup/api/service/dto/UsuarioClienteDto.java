package com.petsup.api.service.dto;

import org.hibernate.validator.constraints.br.CPF;

public class UsuarioClienteDto extends UsuarioDto{
    
    @CPF
    private String CPF;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
