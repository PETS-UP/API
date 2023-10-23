package com.petsup.api.dto.petshop;

import org.springframework.web.multipart.MultipartFile;

public class PetshopAvaliacaoDto {
    private Integer id;

    private double media;

    private String nome;

    private String imagemPerfil;

    public PetshopAvaliacaoDto() {
    }

    public PetshopAvaliacaoDto(Integer id, double media, String nome, String imagemPerfil) {
        this.id = id;
        this.media = media;
        this.nome = nome;
        this.imagemPerfil = imagemPerfil;
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

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }
}
