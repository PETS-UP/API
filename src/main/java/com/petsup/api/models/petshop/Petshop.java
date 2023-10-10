package com.petsup.api.models.petshop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petsup.api.models.Agendamento;
import com.petsup.api.models.AvaliacaoPetshop;
import com.petsup.api.models.Favorito;
import com.petsup.api.models.cliente.ClienteObserver;
import com.petsup.api.models.cliente.ClienteSubscriber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Petshop")
public class Petshop implements ClienteObserver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private String cep;

    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private String numero;

    private String imagemPerfil;

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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime horaAbertura;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
    private LocalTime horaFechamento;

    private List<DayOfWeek> diasFuncionais;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
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

    public LocalTime getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public LocalTime getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(LocalTime horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public List<DayOfWeek> getDiasFuncionais() {
        return diasFuncionais;
    }

    public void setDiasFuncionais(List<DayOfWeek> diasFuncionais) {
        this.diasFuncionais = diasFuncionais;
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
