package com.petsup.api.dto.petshop;

public class PetshopMediaPrecoDto {

    private Integer id;
    private String nome;
    private Double media;

    public PetshopMediaPrecoDto() {
    }

    public PetshopMediaPrecoDto(Integer id, String nome, Double media) {
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
