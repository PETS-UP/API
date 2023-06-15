package com.petsup.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public class UsuarioPetshopDto extends UsuarioDto{

    private int id;
    @Size(min = 14, max = 14)
    @NotBlank
    @Schema(description = "CNPJ do petshop", example = "12.345.678/0001-00")
    private String cnpj;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Nome do petshop", example = "Fofinho Petshop")
    private String razaoSocial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
