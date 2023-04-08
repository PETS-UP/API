package com.petsup.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "Tipo")
public class TipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @OneToMany(mappedBy = "Raca")
    private Set<RacaEntity> racas;

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

    public Set<RacaEntity> getRacas() {
        return racas;
    }

    public void setRacas(Set<RacaEntity> racas) {
        this.racas = racas;
    }
}
