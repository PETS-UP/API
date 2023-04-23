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
}
