package com.petsup.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public abstract class UsuarioDto {

    @NotBlank
    @Size(min=3)
    @Schema(description = "Nome do usuário", example = "Beca Massayuki")
    private String nome;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "beca01@gmail.com")
    private String email;
    @Size(min=6, max=50)
    @NotBlank
    @Schema(description = "Senha do usuário", example = "12345678")
    private String senha;
    @NotBlank
    @Schema(description = "CNPJ do petshop", example = "01001-000")
    private String cep;
    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})" ,
            message = "Indique um telefone válido")
    @Schema(description = "Telefone do usuário", example = "99999-9999")
    private String telefone;


    //Getters and setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
