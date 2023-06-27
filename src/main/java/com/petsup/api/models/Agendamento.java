package com.petsup.api.models;

import com.petsup.api.models.cliente.Pet;
import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.models.petshop.Servico;
import com.petsup.api.models.petshop.UsuarioPetshop;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Future
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "fkPet")
    private Pet fkPet;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private UsuarioPetshop fkPetshop;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private UsuarioCliente fkCliente;

    @ManyToOne
    @JoinColumn(name = "fkServico")
    private Servico fkServico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora)  {
        this.dataHora = dataHora;
    }

    public Pet getFkPet() {
        return fkPet;
    }

    public void setFkPet(Pet fkPet) {
        this.fkPet = fkPet;
    }

    public UsuarioPetshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(UsuarioPetshop fkPetshop) {
        this.fkPetshop = fkPetshop;
    }

    public UsuarioCliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioCliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Servico getFkServico() {
        return fkServico;
    }

    public void setFkServico(Servico fkServico) {
        this.fkServico = fkServico;
    }
}
