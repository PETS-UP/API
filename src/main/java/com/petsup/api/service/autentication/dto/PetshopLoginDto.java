package com.petsup.api.service.autentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PetshopLoginDto {
    @Schema(description = "E-mail do usuário", example = "john@doe.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "SenhaForte123")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
