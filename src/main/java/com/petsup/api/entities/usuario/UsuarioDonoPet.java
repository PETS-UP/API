package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.Pet;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public class UsuarioDonoPet extends Usuario {
    @CPF
    private String CPF;

    @OneToMany(mappedBy = "Pet", fetch = FetchType.LAZY)
    private List<Pet> pets;

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
