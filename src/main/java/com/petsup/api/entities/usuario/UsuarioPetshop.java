package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.AvaliacaoPetshop;
import com.petsup.api.entities.Favorito;
import com.petsup.api.entities.Servico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Table(name = "Petshop")
public class UsuarioPetshop extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "fkPetshop")
    private List<AvaliacaoPetshop> avaliacoes;

    @OneToMany(mappedBy = "fkPetshop")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy = "fkPetshop")
    private List<Servico> servicos;

    @NotBlank
    @Size(min = 6, max = 100)
    private String razaoSocial;

    private String CNPJ;

    @OneToMany(mappedBy = "fkPetshop", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

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
}
