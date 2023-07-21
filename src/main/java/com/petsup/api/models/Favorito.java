package com.petsup.api.models;

import com.petsup.api.models.cliente.Cliente;
import com.petsup.api.models.petshop.Petshop;
import jakarta.persistence.*;

@Entity
@Table(name = "Favorito")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente fkCliente;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private Petshop fkPetshop;

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
}
