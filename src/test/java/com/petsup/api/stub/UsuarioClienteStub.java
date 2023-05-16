package com.petsup.api.stub;

import com.petsup.api.entities.*;
import com.petsup.api.entities.usuario.UsuarioCliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioClienteStub extends UsuarioCliente {

    @Override
    public Integer getId() {
        return 1;
    }

    @Override
    public String getNome() {
        return "Ana Beatriz";
    }

    @Override
    public String getEmail() {
        return "ana@gmail.com";
    }

    @Override
    public String getTelefone() {
        return "11987654321";
    }

    @Override
    public String getCep() {
        return "00100100";
    }

    @Override
    public String getEstado() {
        return "SP";
    }

    @Override
    public String getCidade() {
        return "São Paulo";
    }

    @Override
    public String getBairro() {
        return "Jardim Paulista";
    }

    @Override
    public String getRua() {
        return "Rua Haddock Lobo";
    }

    @Override
    public String getNumero() {
        return "595";
    }

    @Override
    public List<AvaliacaoPetshop> getAvaliacoes() {
        return new ArrayList<>();
    }

    @Override
    public List<Favorito> getFavoritos() {
        return new ArrayList<>();
    }

    @Override
    public List<ClientePetshopSubscriber> getInscritos() {
        return new ArrayList<>();
    }

    @Override
    public LocalDate getDataNasc() {
        return LocalDate.of(2000, 1, 1);
    }

    @Override
    public String getCpf() {
        return "12345678901";
    }

    @Override
    public List<Pet> getPets() {
        return new ArrayList<>();
    }

    @Override
    public List<Agendamento> getAgendamentos() {
        return new ArrayList<>();
    }
}
