package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Table(name = "Petshop")
public class UsuarioPetshop extends Usuario {

<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 6, max = 100)
    private String razaoSocial;

    @CNPJ
=======
>>>>>>> ed37948b45d41e0c9c3efe75793921ba685e34e2
    private String CNPJ;

    private String razaoSocial;

    @OneToMany(mappedBy = "fk_petshop", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
