package com.petsup.api.service.dto;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.NomeServico;
import com.petsup.api.entities.enums.Raca;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AgendamentoDto {

    private Integer id;
    @NotNull
    @Future
    private LocalDateTime dataHora;

    private String nomeCliente;

    private String emailCliente;

    private String nomePetshop;

    private String nomePet;

    private Especie especie;

    private Raca raca;

    private String sexo;

    private NomeServico servico;

    private Double preco;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNomePetshop() {
        return nomePetshop;
    }

    public void setNomePetshop(String nomePetshop) {
        this.nomePetshop = nomePetshop;
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public NomeServico getServico() {
        return servico;
    }

    public void setServico(NomeServico servico) {
        this.servico = servico;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
