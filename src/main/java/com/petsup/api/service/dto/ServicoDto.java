package com.petsup.api.service.dto;

import com.petsup.api.entities.enums.NomeServico;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServicoDto {

    @NotNull
    private NomeServico nome;

    @NotNull
    @DecimalMin("0.0")
    private Double preco;

    @NotBlank
    @Column(length = 300)
    private String descricao;

    public NomeServico getNome() {
        return nome;
    }

    public void setNome(NomeServico nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
