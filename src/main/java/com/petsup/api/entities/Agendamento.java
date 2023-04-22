package com.petsup.api.entities;

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
    @Column(name = "fk_pet")
    private Pet pet;

    @ManyToOne
    @Column(name = "fk_loja")
    private UsuarioLoja donoLoja;

    @ManyToOne
    @Column(name = "fk_donoPet")
    private UsuarioDonoPet donoPet;

    @ManyToOne
    @Column(name = "fk_servicoPetShop")
    private ServicoPetshop servicoPetshop;

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public UsuarioLoja getDonoLoja() {
        return donoLoja;
    }

    public void setDonoLoja(UsuarioLoja donoLoja) {
        this.donoLoja = donoLoja;
    }

    public UsuarioDonoPet getDonoPet() {
        return donoPet;
    }

    public void setDonoPet(UsuarioDonoPet donoPet) {
        this.donoPet = donoPet;
    }

    public ServicoPetshop getServicoPetshop() {
        return servicoPetshop;
    }

    public void setServicoPetshop(ServicoPetshop servicoPetshop) {
        this.servicoPetshop = servicoPetshop;
    }
}
