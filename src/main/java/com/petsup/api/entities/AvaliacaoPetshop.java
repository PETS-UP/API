package com.petsup.api.entities;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
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
    private UsuarioCliente fkCliente;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private UsuarioPetshop fkPetshop;

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

    public UsuarioCliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(UsuarioCliente fkCliente) {
        this.fkCliente = fkCliente;
    }

    public UsuarioPetshop getFkPetshop() {
        return fkPetshop;
    }

    public void setFkPetshop(UsuarioPetshop fkPetshop) {
        this.fkPetshop = fkPetshop;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
