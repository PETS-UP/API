package com.petsup.api.dto;

public class PetshopAvaliacaoDto {
    private Integer id;

    private double media;

    private String nome;

    public PetshopAvaliacaoDto() {
    }

    public PetshopAvaliacaoDto(Integer id, double media, String nome) {
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
