package com.petsup.api.entities;

import com.petsup.api.entities.enums.NomeServico;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "Servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private UsuarioPetshop fkPetshop;

    private NomeServico nome;

    private Double preco;

    private String descricao;

    @OneToMany(mappedBy = "fkServico", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioPetshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(UsuarioPetshop fkPetshop) {
        this.fkPetshop = fkPetshop;
    }

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

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
