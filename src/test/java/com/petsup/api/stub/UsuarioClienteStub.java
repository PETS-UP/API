package com.petsup.api.stub;

import com.petsup.api.entities.usuario.UsuarioCliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsuarioClienteStub {
    UsuarioCliente usuarioClienteStub = new UsuarioCliente();

    public void setUsuarioClienteStub(UsuarioCliente usuarioClienteStub) {
        this.usuarioClienteStub = usuarioClienteStub;
        usuarioClienteStub.setId(1);
        usuarioClienteStub.setNome("Ana Beatriz");
        usuarioClienteStub.setEmail("ana@gmail.com");
        usuarioClienteStub.setTelefone("11987654321");
        usuarioClienteStub.setDataNasc(LocalDate.of(1990, 1, 1));
        usuarioClienteStub.setCpf("12345678901");
        usuarioClienteStub.setCep("00100100");
        usuarioClienteStub.setEstado("SP");
        usuarioClienteStub.setCidade("São Paulo");
        usuarioClienteStub.setBairro("Jardim Paulista");
        usuarioClienteStub.setRua("Rua Haddock Lobo");
        usuarioClienteStub.setNumero("595");
    }
}
