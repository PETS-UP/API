package com.petsup.api.service.dto;

import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.Raca;
import com.petsup.api.entities.usuario.UsuarioCliente;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class PetDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String sexo;

    @NotNull
    @PastOrPresent
    private LocalDate dataNasc;

    @Min(0)
    @Max(1)
    @NotNull
    private Integer castrado;

    @NotNull
    private Especie especie;

    @NotNull
    private Raca raca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getCastrado() {
        return castrado;
    }

    public void setCastrado(Integer castrado) {
        this.castrado = castrado;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

}
