package com.petsup.api.entities;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.entities.usuario.UsuarioPetshop;
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
    @JoinColumn(name = "fk_pet")
    private Pet fk_pet;

    @ManyToOne
    @JoinColumn(name = "fk_petshop")
    private UsuarioPetshop fk_petshop;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private UsuarioCliente fk_cliente;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private Usuario fk_user;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private ServicoPetshop fk_servico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Pet getFk_pet() {
        return fk_pet;
    }

    public void setFk_pet(Pet fk_pet) {
        this.fk_pet = fk_pet;
    }

    public UsuarioPetshop getFk_petshop() {
        return fk_petshop;
    }

    public void setFk_petshop(UsuarioPetshop fk_petshop) {
        this.fk_petshop = fk_petshop;
    }

    public UsuarioCliente getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(UsuarioCliente fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    public ServicoPetshop getFk_servico() {
        return fk_servico;
    }

    public void setFk_servico(ServicoPetshop fk_servico) {
        this.fk_servico = fk_servico;
    }

    public Usuario getFk_user() {
        return fk_user;
    }

    public void setFk_user(Usuario fk_user) {
        this.fk_user = fk_user;
    }
}
