package com.petsup.api.dto.petshop;

import java.time.LocalTime;

public class PetshopAvaliacaoDto {
    private Integer id;

    private Double nota;

    private String nome;

    private String imagemPerfil;

    private Boolean isOpen;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    public PetshopAvaliacaoDto() {
    }

    public PetshopAvaliacaoDto(Integer id, Double nota, String nome, String imagemPerfil, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.id = id;
        this.nota = nota;
        this.nome = nome;
        this.imagemPerfil = imagemPerfil;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
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
}
