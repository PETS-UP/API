package com.petsup.api.util;

import com.petsup.api.entities.usuario.UsuarioPetshop;

public class PetshopAvaliacao {

    private double media;

    private String razaoSocial;

    public PetshopAvaliacao() {
    }

    public PetshopAvaliacao(double media, String razaoSocial) {
        this.media = media;
        this.razaoSocial = razaoSocial;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
