package com.petsup.api.builder;

import com.petsup.api.entities.usuario.UsuarioCliente;

import java.time.LocalDate;

public class UsuarioClienteBuilder {
    public static UsuarioCliente buildUsuarioCliente() {
        UsuarioCliente usuarioCliente = new UsuarioCliente();
        usuarioCliente.setId(1);
        usuarioCliente.setNome("Ana Beatriz");
        usuarioCliente.setEmail("ana@gmail.com");
        usuarioCliente.setSenha("#Teste12");
        usuarioCliente.setDataNasc(LocalDate.of(2000, 1, 1));
        usuarioCliente.setCpf("12345678901");
        usuarioCliente.setCep("1414001");
        usuarioCliente.setEstado("SP");
        usuarioCliente.setCidade("São Paulo");
        usuarioCliente.setBairro("Cerqueira César");
        usuarioCliente.setRua("Rua Haddock Lobo");
        usuarioCliente.setNumero("595");

        return usuarioCliente;
    }
}
