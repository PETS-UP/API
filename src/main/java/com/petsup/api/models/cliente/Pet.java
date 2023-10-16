package com.petsup.api.models.cliente;

import com.petsup.api.models.Agendamento;
import com.petsup.api.models.enums.Especie;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String sexo;
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @OneToMany(mappedBy = "fkPet", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente fkCliente;

    private String imagemPerfil;

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }
}
