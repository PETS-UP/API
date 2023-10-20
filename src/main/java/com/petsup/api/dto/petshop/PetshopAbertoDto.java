package com.petsup.api.dto.petshop;

public class PetshopAbertoDto {
    int id;
    Boolean estaAberto;
    String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEstaAberto() {
        return estaAberto;
    }

    public void setEstaAberto(Boolean estaAberto) {
        this.estaAberto = estaAberto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
