package com.petsup.api.dto.authentication;

public class ClienteTokenDto {
    private Integer clienteId;
    private String nome;
    private String email;
    private String token;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setUserId(Integer clienteId) {
        this.clienteId = clienteId;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
