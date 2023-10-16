package com.petsup.api.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class ClienteDto {

    @NotBlank
    @Size(min=3)
    @Schema(description = "Nome do usuário", example = "Ana Carolina")
    private String nome;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "ana@gmail.com")
    private String email;
    @Size(min=6, max=50)
    @NotBlank
    @Schema(description = "Senha do usuário", example = "12345678")
    private String senha;

    @Schema(description = "CEP do usuário", example = "01001-000")
    private String cep;
    @Pattern(regexp = "^\\d{2}9\\d{8}$" ,
            message = "Indique um telefone válido")
    @Schema(description = "Telefone do usuário", example = "99999-9999")
    private String telefone;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String numero;

    private MultipartFile imagemPerfil;

    @Schema(description = "Data de nascimento do cliente", example = "1999-01-01")
    @Past
    private LocalDate dataNasc;

    @Schema(name = "CPF", description = "CPF do cliente", example = "12345678901")
    @CPF
    private String cpf;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public MultipartFile getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(MultipartFile imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }
}
