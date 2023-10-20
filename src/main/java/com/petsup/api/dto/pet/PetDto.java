package com.petsup.api.dto.pet;

import com.petsup.api.models.enums.Especie;
import jakarta.validation.constraints.*;

public class PetDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String sexo;

    @NotNull
    private Especie especie;

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

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

}
