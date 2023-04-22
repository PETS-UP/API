package com.petsup.api.entities;

import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.Raca;
import com.petsup.api.entities.usuario.UsuarioCliente;
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

    private Especie especie;

    private Raca raca;

    @OneToMany(mappedBy = "fk_pet", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private UsuarioCliente fk_cliente;

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

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public UsuarioCliente getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(UsuarioCliente fk_cliente) {
        this.fk_cliente = fk_cliente;
    }
}
