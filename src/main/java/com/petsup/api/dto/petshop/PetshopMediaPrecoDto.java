package com.petsup.api.dto.petshop;

import java.time.LocalTime;

public class PetshopMediaPrecoDto {

    private Integer id;
    private String nome;
    private Double media;
    private String imagemPerfil;
    private Boolean isOpen;
    private Double nota;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
    private String rua;
    private String numero;

    public PetshopMediaPrecoDto() {
    }

    public PetshopMediaPrecoDto(Integer id, String nome, Double media, String imagemPerfil, LocalTime horaAbertura, LocalTime horaFechamento, String rua, String numero) {
        this.id = id;
        this.nome = nome;
        this.media = media;
        this.imagemPerfil = imagemPerfil;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
        this.rua = rua;
        this.numero = numero;
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

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
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
}
