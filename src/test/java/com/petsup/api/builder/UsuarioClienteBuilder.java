package com.petsup.api.builder;

import com.petsup.api.models.cliente.UsuarioCliente;
import com.petsup.api.dto.authentication.ClienteLoginDto;
import com.petsup.api.dto.authentication.ClienteTokenDto;
import com.petsup.api.dto.cliente.UsuarioClienteDto;

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
        usuarioCliente.setCep("01414001");
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
        usuarioClienteDto.setCep("01414001");
        usuarioClienteDto.setEstado("SP");
        usuarioClienteDto.setCidade("São Paulo");
        usuarioClienteDto.setBairro("Cerqueira César");
        usuarioClienteDto.setRua("Rua Haddock Lobo");
        usuarioClienteDto.setNumero("595");

        return usuarioClienteDto;
    }

    public static ClienteLoginDto buildClienteLoginDto() {
        ClienteLoginDto clienteLoginDto = new ClienteLoginDto();
        clienteLoginDto.setEmail("ana@gmail.com");
        clienteLoginDto.setSenha("#Teste12");

        return clienteLoginDto;
    }

    public static ClienteTokenDto buildClienteTokenDto() {
        ClienteTokenDto clienteTokenDto = new ClienteTokenDto();
        clienteTokenDto.setUserId(1);
        clienteTokenDto.setNome("Ana Beatriz");
        clienteTokenDto.setEmail("ana@gmail.com");
        clienteTokenDto.setToken("token");

        return clienteTokenDto;
    }
}
