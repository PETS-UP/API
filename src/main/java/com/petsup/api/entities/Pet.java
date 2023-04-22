package com.petsup.api.entities;

import com.petsup.api.entities.usuario.UsuarioDonoPet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private char sexo;

    @NotNull
    @PastOrPresent
    private LocalDate dataNasc;

    @Min(0)
    @Max(1)
    @NotNull
    private Integer castrado;
    private char especie;
    private char raca;
    @OneToMany(mappedBy = "fk_pet", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;
    @ManyToOne
    @JoinColumn(name = "fk_dono")
    private UsuarioDonoPet fk_dono;

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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getCastrado() {
        return castrado;
    }

    public void setCastrado(Integer castrado) {
        this.castrado = castrado;
    }

    public char getEspecie() {
        return especie;
    }

    public void setEspecie(char especie) {
        this.especie = especie;
    }

    public char getRaca() {
        return raca;
    }

    public void setRaca(char raca) {
        this.raca = raca;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public UsuarioDonoPet getFk_dono() {
        return fk_dono;
    }

    public void setFk_dono(UsuarioDonoPet fk_dono) {
        this.fk_dono = fk_dono;
    }
}
