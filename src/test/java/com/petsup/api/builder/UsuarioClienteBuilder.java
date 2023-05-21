package com.petsup.api.builder;

import com.petsup.api.entities.usuario.UsuarioCliente;
import com.petsup.api.service.dto.UsuarioClienteDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static List<UsuarioCliente> buildListaUsuarioCliente() {
        List<UsuarioCliente> lista = new ArrayList<>();
        UsuarioCliente cliente1 = buildUsuarioCliente();
        UsuarioCliente cliente2 = buildUsuarioCliente();
        UsuarioCliente cliente3 = buildUsuarioCliente();
        cliente2.setId(2);
        cliente3.setId(3);

        lista.add(cliente1);
        lista.add(cliente2);
        lista.add(cliente3);

        return lista;
    }

    public static UsuarioClienteDto buildUsuarioClienteDto() {
        UsuarioClienteDto usuarioClienteDto = new UsuarioClienteDto();
        usuarioClienteDto.setNome("Ana Beatriz");
        usuarioClienteDto.setEmail("ana@gmail.com");
        usuarioClienteDto.setSenha("#Teste12");
        usuarioClienteDto.setDataNasc(LocalDate.of(2000, 1, 1));
        usuarioClienteDto.setCpf("12345678901");
        usuarioClienteDto.setCep("1414001");
        usuarioClienteDto.setEstado("SP");
        usuarioClienteDto.setCidade("São Paulo");
        usuarioClienteDto.setBairro("Cerqueira César");
        usuarioClienteDto.setRua("Rua Haddock Lobo");
        usuarioClienteDto.setNumero("595");

        return usuarioClienteDto;
    }
}
