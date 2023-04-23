package com.petsup.api.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public class UsuarioPetshopDto extends UsuarioDto{
    @Size(min = 14, max = 14)
    @NotBlank
    private String CNPJ;
    @Size(min=5)
    @NotBlank
    private String RazaoSocial;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }
}
