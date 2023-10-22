package com.petsup.api.dto.petshop;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class PetshopDto {

    private int id;

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
    @Size(min = 14, max = 14)
    @NotBlank
    @Schema(description = "CNPJ do petshop", example = "12.345.678/0001-00")
    private String cnpj;

    @NotBlank
    @Size(min = 6, max = 100)
    @Schema(description = "Nome do petshop", example = "Fofinho Petshop")
    private String razaoSocial;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private List<DayOfWeek> diasFuncionais;

    private MultipartFile imagemPerfil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalTime getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public LocalTime getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(LocalTime horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public List<DayOfWeek> getDiasFuncionais() {
        return diasFuncionais;
    }

    public void setDiasFuncionais(List<DayOfWeek> diasFuncionais) {
        this.diasFuncionais = diasFuncionais;
    }

    public MultipartFile getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(MultipartFile imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }
}
