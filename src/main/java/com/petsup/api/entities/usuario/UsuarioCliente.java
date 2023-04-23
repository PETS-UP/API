package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.AvaliacaoPetshop;
import com.petsup.api.entities.Favorito;
import com.petsup.api.entities.Pet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class UsuarioCliente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "fkCliente")
    private List<AvaliacaoPetshop> avaliacoes;

    @OneToMany(mappedBy = "fkCliente")
    private List<Favorito> favoritos;

    @Past
    private LocalDate dataNasc;

    @CPF
    private String CPF;

    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.LAZY)
    private List<Pet> pets;

    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public List<AvaliacaoPetshop> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoPetshop> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
