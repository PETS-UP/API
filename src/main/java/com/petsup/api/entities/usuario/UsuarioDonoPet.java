package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.Pet;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
public class UsuarioDonoPet extends Usuario {
    @CPF
    private String CPF;

    @OneToMany(mappedBy = "fk_dono", fetch = FetchType.LAZY)
    private List<Pet> pets;

//    @OneToMany(mappedBy = "fk_donoPet", fetch = FetchType.LAZY)
//    private List<Agendamento> agendamentos;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
