package com.petsup.api.entities;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import jakarta.persistence.*;

@Entity
@Table(name = "Favorito")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private UsuarioCliente fkCliente;

    @ManyToOne
    @JoinColumn(name = "fkPetshop")
    private UsuarioPetshop fkPetshop;

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
}
