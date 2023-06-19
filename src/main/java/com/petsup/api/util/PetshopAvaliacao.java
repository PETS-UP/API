package com.petsup.api.util;

public class PetshopAvaliacao {
    private Integer id;

    private double media;

    private String nome;

    public PetshopAvaliacao() {
    }

    public PetshopAvaliacao(Integer id, double media, String nome) {
        this.id = id;
        this.media = media;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
