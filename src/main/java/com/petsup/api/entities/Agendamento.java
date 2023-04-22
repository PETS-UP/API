package com.petsup.api.entities;

import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioDonoPet;
import com.petsup.api.entities.usuario.UsuarioLoja;
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

//    @ManyToOne
//    @JoinColumn(name = "fk_loja")
//    private UsuarioLoja fk_loja;

//    @ManyToOne
//    @JoinColumn(name = "fk_donoPet")
//    private UsuarioDonoPet fk_donoPet;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private Usuario fk_user;

    @ManyToOne
    @JoinColumn(name = "fk_servicoPetShop")
    private ServicoPetshop fk_servicoPetShop;

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

    public Usuario getFk_user() {
        return fk_user;
    }

    public void setFk_user(Usuario fk_user) {
        this.fk_user = fk_user;
    }

    public ServicoPetshop getFk_servicoPetShop() {
        return fk_servicoPetShop;
    }

    public void setFk_servicoPetShop(ServicoPetshop fk_servicoPetShop) {
        this.fk_servicoPetShop = fk_servicoPetShop;
    }
}
