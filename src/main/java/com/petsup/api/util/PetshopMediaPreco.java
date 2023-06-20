package com.petsup.api.util;

public class PetshopMediaPreco {

    private Integer id;
    private String nome;
    private Double media;

    public PetshopMediaPreco() {
    }

    public PetshopMediaPreco(Integer id, String nome, Double media) {
        this.id = id;
        this.nome = nome;
        this.media = media;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }
}
