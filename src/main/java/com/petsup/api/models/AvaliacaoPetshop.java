package com.petsup.api.models;

import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.petshop.Petshop;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Avaliacao")
public class AvaliacaoPetshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente fkCliente;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private Petshop fkPetshop;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double nota;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Petshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(Petshop fkPetshop) {
        this.fkPetshop = fkPetshop;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
