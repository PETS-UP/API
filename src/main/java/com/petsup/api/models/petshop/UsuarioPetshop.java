package com.petsup.api.models.petshop;

import com.petsup.api.models.Agendamento;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.Favorito;
import com.petsup.api.models.Usuario;
import com.petsup.api.models.cliente.ClienteObserver;
import com.petsup.api.models.cliente.ClienteSubscriber;
import jakarta.persistence.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Entity
@Table(name = "Petshop")
public class UsuarioPetshop extends Usuario implements ClienteObserver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "fkPetshop")
    private List<AvaliacaoPetshop> avaliacoes;

    @OneToMany(mappedBy = "fkPetshop")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy = "fkPetshop")
    private List<Servico> servicos;

    private String razaoSocial;

    private String cnpj;

    @OneToMany(mappedBy = "fkPetshop", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    @OneToMany(mappedBy = "fkPetshop")
    private List<ClienteSubscriber> inscritos;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public List<AvaliacaoPetshop> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoPetshop> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<ClienteSubscriber> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<ClienteSubscriber> inscritos) {
        this.inscritos = inscritos;
    }

    public void inscricao(ClienteSubscriber listener){
        inscritos.add(listener);
    }

    public void desinscricao(ClienteSubscriber listener){
        inscritos.remove(listener);
    }

    // Observer
    @Override
    public void atualiza(JavaMailSender enviador, String remetente, String destinatario, double preco) {
        for (ClienteSubscriber assinante : inscritos) {
            assinante.notifica(enviador, remetente, destinatario, preco); // Chamada do método de envio de emails
                                                                          // do observador (subscriber)
        }
    }
}
