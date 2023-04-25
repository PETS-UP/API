package com.petsup.api.entities.usuario;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.AvaliacaoPetshop;
import com.petsup.api.entities.Favorito;
import com.petsup.api.entities.Servico;
import jakarta.persistence.*;

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

    private String razaoSocial;

    private String cnpj;

    @OneToMany(mappedBy = "fkPetshop", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos;

    private List<UsuarioCliente> inscritos;

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

    public List<UsuarioCliente> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<UsuarioCliente> inscritos) {
        this.inscritos = inscritos;
    }

    // Observer
    public void inscricao(UsuarioCliente listener){
        inscritos.add(listener);
    }

    public void desinscricao(UsuarioCliente listener){
        inscritos.remove(listener);
    }

    public void notifica(String emailRemetente, String emailDestinatario, String host){
        inscritos.forEach(listener -> listener.atualiza(emailRemetente, emailDestinatario, host));
    }
}
